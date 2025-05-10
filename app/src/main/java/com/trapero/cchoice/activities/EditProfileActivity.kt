package com.trapero.cchoice.activities

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.trapero.cchoice.R
import com.trapero.cchoice.api.ApiHelper
import com.trapero.cchoice.session.Session
import java.io.IOException

class EditProfileActivity : AppCompatActivity() {

    private lateinit var editFirstName: EditText
    private lateinit var editLastName: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var editAddress: EditText
    private lateinit var editContactNumber: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        // Initialize UI elements
        editFirstName = findViewById(R.id.edit_first_name)
        editLastName = findViewById(R.id.edit_last_name)
        editEmail = findViewById(R.id.edit_email)
        editAddress = findViewById(R.id.edit_address)
        editContactNumber = findViewById(R.id.edit_contact_number)
        saveButton = findViewById(R.id.save_button)

        editFirstName.setText(Session.user_first_name)
        editLastName.setText(Session.user_last_name)
        editEmail.setText(Session.user_email)
        editAddress.setText(Session.user_address)
        editContactNumber.setText(Session.user_phone)

        saveButton.setOnClickListener {
            val firstName = editFirstName.text.toString()
            val lastName = editLastName.text.toString()
            val email = editEmail.text.toString()
            val address = editAddress.text.toString()
            val contactNumber = editContactNumber.text.toString()

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || address.isEmpty() || contactNumber.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val apiHelper = ApiHelper()
            apiHelper.setHeaders(mapOf(
                "Authorization" to "Bearer "+Session.authToken
            ))
            apiHelper.setBody<JsonObject>(mapOf(
                "user_first_name" to firstName,
                "user_last_name" to lastName,
                "user_email" to email,
                "user_phone" to contactNumber,
                "user_address" to address
            ))
            .patch(
                endpoint = "/me",
                callback = object: okhttp3.Callback {
                    override fun onFailure(call: okhttp3.Call, e: IOException) {
                        this@EditProfileActivity.runOnUiThread{Toast.makeText(this@EditProfileActivity, e.message, Toast.LENGTH_LONG).show()}
                    }

                    override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                        if (response.isSuccessful) {
                            Session.apply {
                                user_first_name = firstName
                                user_last_name = lastName
                                user_email = email
                                user_address = address
                                user_phone = contactNumber
                            }
                            this@EditProfileActivity.runOnUiThread{
                                Toast.makeText(this@EditProfileActivity, "Update Successful", Toast.LENGTH_SHORT).show()
                                this@EditProfileActivity.finish()
                            }

                        } else {
                            val mapType = object : TypeToken<Map<String, Any>>() {}.type
                            val responseMap: Map<String, Any> = Gson().fromJson(response.body?.string(), mapType)
                            val errors = responseMap["errors"] as? Map<String, ArrayList<String>>
                            val error = responseMap["error"] as? Map<String, Any>
                            val firstErrorMessage = errors?.values?.firstOrNull()?.firstOrNull()

                            this@EditProfileActivity.runOnUiThread {
                                if (firstErrorMessage != null)
                                    Toast.makeText(
                                        this@EditProfileActivity,
                                        firstErrorMessage,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                else if (error?.get("message") as? String != null)
                                    Toast.makeText(
                                        this@EditProfileActivity,
                                        error["message"].toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                else
                                    Toast.makeText(
                                        this@EditProfileActivity,
                                        "No errors returned but unsuccessful",
                                        Toast.LENGTH_SHORT
                                    ).show()
                            }
                        }
                    }
                }
            )

        }
    }
}
