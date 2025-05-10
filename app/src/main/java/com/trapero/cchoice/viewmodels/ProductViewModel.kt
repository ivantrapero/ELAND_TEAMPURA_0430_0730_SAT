package com.trapero.cchoice.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.trapero.cchoice.api.ApiHelper
import com.trapero.cchoice.models.Product
import com.trapero.cchoice.session.Session
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class ProductViewModel : ViewModel() {

    private val productList = mutableListOf<Product>()
    private val _products = MutableLiveData<List<Product>>()
    private val _toast = MutableLiveData<String>()

    val products: LiveData<List<Product>> get() = _products
    val toast: LiveData<String> get() = _toast

    init {
        loadInitialProducts()
    }

    private fun loadInitialProducts() {
        val apiHelper = ApiHelper()
        apiHelper.setHeaders(mapOf(
            "Authorization" to "Bearer "+ Session.authToken
        ))

        apiHelper.get("/product", object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                _toast.postValue(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val mapType = object : TypeToken<Map<String, Any>>() {}.type
                    val responseMap: Map<String, Any> = Gson().fromJson(response.body?.string(), mapType)
                    val products = responseMap["data"] as? ArrayList<Map<String,Any>>
                    val productsList = mutableListOf<Product>()

                    products?.let{
                        products.forEach{
                            productsList.add(
                                Product(
                                    it["prod_id"] as? Int ?: 0,
                                    it["prod_name"] as? String ?: "",
                                    it["prod_buy_price"] as? Double ?: 0.0,
                                    (it["prod_buy_nondiscounted"] as? Double)?.toInt() ?: 0,
                                    (0..3).random().toFloat(),
                                    (100..200).random(),
                                    it["prod_image"] as? String,
                                    it["prod_description"] as? String
                                )
                            )

                            val toLog = listOf <Any?>(
                                it["prod_id"] as? Int ?: 0,
                                it["prod_name"] as? String ?: "",
                                it["prod_buy_price"] as? Double ?: 0.0,
                                (it["prod_buy_nondiscounted"] as? Double)?.toInt() ?: 0,
                                (0..3).random().toFloat(),
                                (100..200).random(),
                                it["prod_image"] as? String,
                                it["prod_description"] as? String
                            )

                            Log.i("tett",toLog.toString())
                        }
                    }

//                    _products.postValue(productsList)

                }else{
                    val mapType = object : TypeToken<Map<String, Any>>() {}.type
                    val responseMap: Map<String, Any> = Gson().fromJson(response.body?.string(), mapType)
                    val errors = responseMap["errors"] as? Map<String, ArrayList<String>>
                    val error = responseMap["error"] as? Map<String, Any>
                    val firstErrorMessage = errors?.values?.firstOrNull()?.firstOrNull()

                    if(firstErrorMessage != null)
                        _toast.postValue(firstErrorMessage)
                    else if (error?.get("message") as? String != null)
                        _toast.postValue(error["message"].toString())
                    else
                        _toast.postValue("No errors returned but unsuccessful")
                }

            }

        })

        _products.value = productList
    }

    fun setTempProducts(tempProducts: List<Product>) {
        productList.clear()
        productList.addAll(tempProducts)
        _products.value = productList
    }
}
