package com.trapero.cchoice.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.trapero.cchoice.R
import com.trapero.cchoice.databinding.ActivityLoginBinding
import com.trapero.cchoice.viewmodels.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup DataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Observe navigation to registration screen
        viewModel.navigateToRegister.observe(this, { shouldNavigate ->
        if (shouldNavigate) {
            navigateToRegister()
            viewModel.doneNavigatingToRegister()
        }
        })

        // Observe login success
        viewModel.loginSuccess.observe(this, { isSuccess ->
        if (isSuccess) {
            navigateToMainScreen() // Replace with your main activity
            viewModel.doneLoggingIn()
        }
        })

        // Observe error messages
        viewModel.errorMessage.observe(this, { errorMessage ->
        if (!errorMessage.isNullOrEmpty()) {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            viewModel.doneShowingError()
        }
        })

        // Observe loading state (you might want to connect this to a ProgressBar)
        viewModel.isLoggingIn.observe(this, { isLoggingIn ->
                binding.progressBarLogin.visibility = if (isLoggingIn) android.view.View.VISIBLE else android.view.View.GONE
        binding.loginButton.isEnabled = !isLoggingIn
        })

        // Observe "Forgot Password" click (if you want to handle it in the Activity)
        viewModel.navigateToForgotPassword.observe(this, { shouldNavigate ->
        if (shouldNavigate) {
            // Implement your navigation logic for forgot password
            Toast.makeText(this, "Forgot Password Clicked", Toast.LENGTH_SHORT).show()
            viewModel.doneNavigatingToForgotPassword()
        }
        })


        val emailEditText = findViewById<EditText>(R.id.emailEditTextLogin)

        // Add EditText listener to update the email in the ViewModel
        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Update the email in the ViewModel when the text changes
                viewModel.email.value = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        val passswordEditText = findViewById<EditText>(R.id.passwordEditTextLogin)

        // Add EditText listener to update the email in the ViewModel
        passswordEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Update the email in the ViewModel when the text changes
                viewModel.password.value = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToMainScreen() {
        // Replace YourMainActivity::class.java with the actual class of your main screen
        val intent = Intent(this, DashboardActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}
