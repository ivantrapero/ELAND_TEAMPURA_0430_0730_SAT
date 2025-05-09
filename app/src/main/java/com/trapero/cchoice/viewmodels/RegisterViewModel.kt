package com.trapero.cchoice.viewmodels

import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.trapero.cchoice.api.ApiHelper
import com.trapero.cchoice.api.RetrofitClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class RegisterViewModel : ViewModel() {

    private val _firstName = MutableLiveData<String>()
    var firstName: MutableLiveData<String>
        get() = _firstName
        set(value) { _firstName.value = value.value }

    private val _lastName = MutableLiveData<String>()
    var lastName: MutableLiveData<String>
        get() = _lastName
        set(value) { _lastName.value = value.value }

    private val _email = MutableLiveData<String>()
    var email: MutableLiveData<String>
        get() = _email
        set(value) { _email.value = value.value }

    private val _password = MutableLiveData<String>()
    var password: MutableLiveData<String>
        get() = _password
        set(value) { _password.value = value.value }

    private val _confirmPassword = MutableLiveData<String>()
    var confirmPassword: MutableLiveData<String>
        get() = _confirmPassword
        set(value) { _confirmPassword.value = value.value }

    private val _address = MutableLiveData<String>()
    var address: MutableLiveData<String>
        get() = _address
        set(value) { _address.value = value.value }

    private val _contactNumber = MutableLiveData<String>()
    var contactNumber: MutableLiveData<String>
        get() = _contactNumber
        set(value) { _contactNumber.value = value.value }

    private val _errorMessage = MutableLiveData<String?>()
    var errorMessage: MutableLiveData<String?>
        get() = _errorMessage
        set(value) { _errorMessage.value = value.value }

    private val _navigateToLogin = MutableLiveData(false)
    var navigateToLogin: MutableLiveData<Boolean>
        get() = _navigateToLogin
        set(value) { _navigateToLogin.value = value.value }

    private val _registrationSuccess = MutableLiveData(false)
    var registrationSuccess: MutableLiveData<Boolean>
        get() = _registrationSuccess
        set(value) { _registrationSuccess.value = value.value }

    private val _isRegistering = MutableLiveData(false)
    var isRegistering: MutableLiveData<Boolean>
        get() = _isRegistering
        set(value) { _isRegistering.value = value.value }


    val signUpClickedListener = View.OnClickListener {
        onSignUpClicked()
    }

    fun onSignUpClicked() {

        isRegistering.value = true


        val apiHelper = ApiHelper()

        apiHelper.setBody<JsonObject>(mapOf(
            "user_first_name" to firstName.value.toString(),
            "user_last_name" to lastName.value.toString(),
            "user_email" to email.value.toString(),
            "user_password" to password.value.toString(),
            "user_phone" to contactNumber.value.toString(),
            "user_address" to address.value.toString(),
            "user_type" to 0
        ))
        .post("/register", object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                isRegistering.postValue(false)
                errorMessage.postValue(e.message)
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                isRegistering.postValue(false)
                if (response.isSuccessful) {
                    registrationSuccess.postValue(true)
                } else {
                    val mapType = object : TypeToken<Map<String, Any>>() {}.type
                    val responseMap: Map<String, Any> = Gson().fromJson(response.body?.string(), mapType)
                    val errors = responseMap["errors"] as? Map<String, ArrayList<String>>
                    val firstErrorMessage = errors?.values?.firstOrNull()?.firstOrNull()

                    firstErrorMessage?.let {
                        errorMessage.postValue(it)  // Post the first error message
                    } ?: errorMessage.postValue("No error found")

                }
            }
        })
    }

    fun onLoginClicked() {
        navigateToLogin.value = true
    }

    fun doneNavigatingToLogin() {
        navigateToLogin.value = false
    }

    fun doneRegistering() {
        registrationSuccess.value = false
    }

    fun doneShowingError() {
        errorMessage.value = null
    }
}
