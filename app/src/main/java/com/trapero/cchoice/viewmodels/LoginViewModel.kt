package com.trapero.cchoice.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.trapero.cchoice.api.ApiHelper
import com.trapero.cchoice.session.Session
import kotlin.Unit
import kotlin.jvm.functions.Function1
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class LoginViewModel : ViewModel() {

    // Using correct setter with backing field
    private val _email = MutableLiveData<String>()
    var email: MutableLiveData<String>
        get() = _email
        set(value) { _email.value = value.value }

    private val _password = MutableLiveData<String>()
    var password: MutableLiveData<String>
        get() = _password
        set(value) { _password.value = value.value }

    private val _navigateToRegister = MutableLiveData<Boolean>(false)
    var navigateToRegister: MutableLiveData<Boolean>
        get() = _navigateToRegister
        set(value) { _navigateToRegister.value = value.value }

    private val _loginSuccess = MutableLiveData<Boolean>(false)
    var loginSuccess: MutableLiveData<Boolean>
        get() = _loginSuccess
        set(value) { _loginSuccess.value = value.value }

    private val _errorMessage = MutableLiveData<String?>()
    var errorMessage: MutableLiveData<String?>
        get() = _errorMessage
        set(value) { _errorMessage.value = value.value }

    private val _isLoggingIn = MutableLiveData<Boolean>(false)
    var isLoggingIn: MutableLiveData<Boolean>
        get() = _isLoggingIn
        set(value) { _isLoggingIn.value = value.value }

    private val _navigateToForgotPassword = MutableLiveData<Boolean>(false)
    var navigateToForgotPassword: MutableLiveData<Boolean>
        get() = _navigateToForgotPassword
        set(value) { _navigateToForgotPassword.value = value.value }

    fun onLoginClicked() {
        val emailValue = email.value
        val passwordValue = password.value

        isLoggingIn.value = true

        val apiHelper = ApiHelper()

        apiHelper.setBody <JsonObject>( mapOf<String,Any>(
            "user_email" to email.value.toString(),
            "user_password" to password.value.toString()
        ))
        .post("/login", object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                isLoggingIn.postValue(false)
                errorMessage.postValue(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                isLoggingIn.postValue(false)
                if (response.isSuccessful) {
                    loginSuccess.postValue(true)
                    val mapType = object : TypeToken<Map<String, Any>>() {}.type
                    val responseMap: Map<String, Any> = Gson().fromJson(response.body?.string(), mapType)
                    val token = responseMap["token"] as? String
                    token?.let { Session.authToken = it }
                } else {
                    val mapType = object : TypeToken<Map<String, Any>>() {}.type
                    val responseMap: Map<String, Any> = Gson().fromJson(response.body?.string(), mapType)
                    Log.i("tesstKim", responseMap.toString())
                    val errors = responseMap["errors"] as? Map<String, ArrayList<String>>
                    val error = responseMap["error"] as? Map<String, Any>
                    val firstErrorMessage = errors?.values?.firstOrNull()?.firstOrNull()

                    if(firstErrorMessage != null)
                        errorMessage.postValue(firstErrorMessage)
                    else if (error?.get("message") as? String != null)
                        errorMessage.postValue(error["message"].toString())
                    else
                        errorMessage.postValue("No errors specified")
                }
            }
        })
    }

    fun onSignUpClicked() {
        navigateToRegister.value = true
    }

    fun onForgotPasswordClicked() {
        navigateToForgotPassword.value = true
    }

    fun doneNavigatingToRegister() {
        navigateToRegister.value = false
    }

    fun doneLoggingIn() {
        loginSuccess.value = false
    }

    fun doneShowingError() {
        errorMessage.value = null
    }

    fun doneNavigatingToForgotPassword() {
        navigateToForgotPassword.value = false
    }
}
