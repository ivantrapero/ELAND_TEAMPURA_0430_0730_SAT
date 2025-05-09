package com.trapero.cchoice.api.services

import com.google.gson.JsonObject
import com.trapero.cchoice.api.ApiHelper
import okhttp3.Callback

class UserService {
    fun login(email: String, password: String, callback: Callback){
        val apiHelper = ApiHelper()


        apiHelper.setBody <JsonObject>( mapOf<String,Any>(
            "user_email" to email,
            "user_password" to password
        ) )

        apiHelper.post(
            endpoint = "/login",
            callback
        )
    }
}