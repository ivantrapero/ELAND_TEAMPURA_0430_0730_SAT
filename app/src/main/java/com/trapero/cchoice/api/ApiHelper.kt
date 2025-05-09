package com.trapero.cchoice.api

import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.Normalizer.Form

class ApiHelper {
    companion object{
        private const val BASEPATH = "http://3.26.75.90/api";
        enum class Method{GET, POST, PATCH, DELETE}
        enum class BodyType{ JSON, FormBody }

    }

    private val client = OkHttpClient()
    private val urlParams  = JsonObject();
    private val reqHeaders = JsonObject();
    var bodyType: BodyType? = null
        private set
    private val bodyJson = JsonObject();
    private val bodyForm = FormBody.Builder();


    fun setHeaders(callback: (JsonObject) -> Unit): ApiHelper{
        callback(reqHeaders)
        return this
    }

    fun setParams(callback: (JsonObject) -> Unit): ApiHelper{
        callback(urlParams)
        return this
    }

    fun setParams(params: Map<String, Any>){
        val gson = Gson()

        params.forEach{
            urlParams.add(it.key, gson.toJsonTree(it.value))
        }
    }

    fun setHeaders(params: Map<String, Any>){
        val gson = Gson()

        params.forEach{
            reqHeaders.add(it.key, gson.toJsonTree(it.value))
        }
    }

    // For setting a JSON body
    fun setJsonBody(callback: (JsonObject) -> Unit): ApiHelper {
        if (bodyType == BodyType.FormBody) {
            throw IllegalStateException("Cannot set JSON body after FormBody has been set.")
        }

        callback(bodyJson)
        bodyType = BodyType.JSON
        return this
    }

    // For setting a Form body
    fun setFormBody(callback: (FormBody.Builder) -> Unit): ApiHelper {
        if (bodyType == BodyType.JSON) {
            throw IllegalStateException("Cannot set FormBody after JSON body has been set.")
        }

        callback(bodyForm)
        bodyType = BodyType.FormBody
        return this
    }


    inline fun <reified T> setBody(body: Map<String, Any>): ApiHelper {
        when (T::class) {
            JsonObject::class -> {
                val gson = Gson()

                this@ApiHelper.setJsonBody {
                    body.forEach { (key, value) ->
                        it.add(key, gson.toJsonTree(value))
                    }
                }
            }
            FormBody::class -> {
                this@ApiHelper.setFormBody {
                    body.forEach { (key, value) ->
                        when (value) {
                            is Boolean -> it.add(key, value.toString())
                            is Char -> it.add(key, value.toString())
                            is Number -> it.add(key, value.toString())
                            is String -> it.add(key, value)
                            else -> throw IllegalArgumentException("Unsupported body value type")
                        }
                    }
                }
            }
            else -> throw IllegalArgumentException("Unsupported body type")
        }
        return this
    }


    private fun jsonObjectToMap(jsonObject: JsonObject): Map<String, String> {
        return jsonObject.entrySet().associate { it.key to it.value.toString().trim('"') }
    }

    private fun buildUrl(endpoint: String): String {
        if (urlParams.size() == 0) return BASEPATH + endpoint

        val paramMap = jsonObjectToMap(urlParams)
        val query = paramMap.entries.joinToString("&") { "${it.key}=${it.value}" }

        if(endpoint.contains("?"))
            return "$BASEPATH$endpoint&$query"
        else
            return "$BASEPATH$endpoint?$query"

    }


    fun get(endpoint: String, callback: Callback?){ this.request(method = Method.GET, endpoint = endpoint, headers = null, jsonBody = null, formBody = null, callback = callback) }

    fun post(endpoint: String, callback: Callback?){ this.request(method = Method.POST, endpoint = endpoint, headers = null, jsonBody = null, formBody = null, callback = callback) }

    fun patch(endpoint: String, callback: Callback?){ this.request(method = Method.PATCH, endpoint = endpoint, headers = null, jsonBody = null, formBody = null, callback = callback) }

    fun delete(endpoint: String, callback: Callback?){ this.request(method = Method.DELETE, endpoint = endpoint, headers = null, jsonBody = null, formBody = null, callback = callback) }


    fun get(endpoint: String,
            callback: Callback?,
            formBody: ((FormBody.Builder) -> Unit)? = null,
            jsonBody: ((JsonObject) -> Unit)? = null,
            headers: ((JsonObject) -> Unit)? = null) {

        this.request(method = Method.GET, endpoint = endpoint, headers = headers, jsonBody = jsonBody, formBody = formBody, callback = callback)
    }

    fun post(endpoint: String,
             callback: Callback?,
             formBody: ((FormBody.Builder) -> Unit)? = null,
             jsonBody: ((JsonObject) -> Unit)? = null,
             headers: ((JsonObject) -> Unit)? = null) {

        this.request(method = Method.POST, endpoint = endpoint, headers = headers, jsonBody = jsonBody, formBody = formBody, callback = callback)
    }

    fun patch(endpoint: String,
              callback: Callback?,
              formBody: ((FormBody.Builder) -> Unit)? = null,
              jsonBody: ((JsonObject) -> Unit)? = null,
              headers: ((JsonObject) -> Unit)? = null) {

        this.request(method = Method.PATCH, endpoint = endpoint, headers = headers, jsonBody = jsonBody, formBody = formBody, callback = callback)
    }

    fun delete(endpoint: String,
               callback: Callback?,
               formBody: ((FormBody.Builder) -> Unit)? = null,
               jsonBody: ((JsonObject) -> Unit)? = null,
               headers: ((JsonObject) -> Unit)? = null) {

        this.request(method = Method.DELETE, endpoint = endpoint, headers = headers, jsonBody = jsonBody, formBody = formBody, callback = callback)
    }

    private fun request(
        endpoint: String,
        method: Method,
        headers: ((JsonObject) -> Unit)?,
        jsonBody: ((JsonObject) -> Unit)?,
        formBody: ((FormBody.Builder) -> Unit)?,
        callback: Callback?,
    ){

        headers?.let {
            this.setHeaders (it)
        }

        jsonBody?.let {
            this.setJsonBody (it)
        }

        formBody?.let {
            this.setFormBody(it)
        }




        val requestBuilder = Request.Builder().url( buildUrl(endpoint) )

        for ((key, value) in jsonObjectToMap(reqHeaders)) {
            requestBuilder.addHeader(key, value)
        }

        val requestBody: RequestBody? = when (method) {
            Method.POST, Method.PATCH, Method.DELETE -> {
                if (bodyType == BodyType.JSON) {
                    val mediaType = "application/json; charset=utf-8".toMediaType()
                    bodyJson.toString().toRequestBody(mediaType)
                } else {
                    bodyForm.build()
                }
            }
            else -> null
        }

        when (method) {
            Method.GET -> requestBuilder.get()
            Method.POST -> requestBuilder.post(requestBody!!)
            Method.PATCH -> requestBuilder.patch(requestBody!!)
            Method.DELETE -> requestBuilder.delete(requestBody)
        }

        callback?.let{
            return client.newCall(requestBuilder.build()).enqueue(callback)
        }

        client.newCall(requestBuilder.build())

    }
}