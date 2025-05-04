package com.trapero.cchoice.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.trapero.cchoice.viewmodels.LoginViewModel;
import com.trapero.cchoice.R;
import com.trapero.cchoice.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel viewModel;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        // Observe navigation to registration screen
        viewModel.getNavigateToRegister().observe(this, shouldNavigate -> {
            if (shouldNavigate) {
                navigateToRegister();
                viewModel.doneNavigatingToRegister();
            }
        });

        // Observe login success
        viewModel.getLoginSuccess().observe(this, isSuccess -> {
            if (isSuccess) {
                navigateToMainScreen(); // Replace with your main activity
                viewModel.doneLoggingIn();
            }
        });

        // Observe error messages
        viewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
                viewModel.doneShowingError();
            }
        });

        // Observe loading state (you might want to connect this to a ProgressBar)
        viewModel.getIsLoggingIn().observe(this, isLoggingIn -> {
            // Assuming you have a ProgressBar with the ID progressBarLogin
            if (binding.progressBarLogin != null) {
                binding.progressBarLogin.setVisibility(isLoggingIn ? android.view.View.VISIBLE : android.view.View.GONE);
            }
            binding.loginButton.setEnabled(!isLoggingIn);
        });

        // Observe "Forgot Password" click (if you want to handle it in the Activity)
        viewModel.getNavigateToForgotPassword().observe(this, shouldNavigate -> {
            if (shouldNavigate) {
                // Implement your navigation logic for forgot password
                Toast.makeText(this, "Forgot Password Clicked", Toast.LENGTH_SHORT).show();
                viewModel.doneNavigatingToForgotPassword();
            }
        });
    }

    private void navigateToRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void navigateToMainScreen() {
        // Replace YourMainActivity.class with the actual class of your main screen
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Prevent going back to login screen
    }

}