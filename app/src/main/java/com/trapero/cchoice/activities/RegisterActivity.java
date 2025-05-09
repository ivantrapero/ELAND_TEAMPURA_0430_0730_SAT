package com.trapero.cchoice.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import com.trapero.cchoice.activities.DashboardActivity;
import com.trapero.cchoice.R;
import com.trapero.cchoice.viewmodels.RegisterViewModel;
import com.trapero.cchoice.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private RegisterViewModel viewModel;
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        // Observe navigation to login screen
        viewModel.getNavigateToLogin().observe(this, shouldNavigate -> {
            if (shouldNavigate) {
                navigateToLogin();
                // Reset the LiveData to prevent multiple navigations
                viewModel.doneNavigatingToLogin();
            }
        });

        // Observe registration success
        viewModel.getRegistrationSuccess().observe(this, isSuccess -> {
            if (isSuccess) {
                showRegistrationSuccess();
                // Optionally navigate to another screen or reset the flag
                viewModel.doneRegistering();
            }
        });

        // Observe error messages
        viewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
                // Reset the error message to prevent showing it multiple times
                viewModel.doneShowingError();
            }
        });

        // Observe loading state
        viewModel.isRegistering().observe(this, isRegistering -> {
            // You can show/hide a progress bar here based on the value of isRegistering
            if (isRegistering) {
                // Show progress bar
            } else {
                // Hide progress bar
            }
            // You might also want to disable/enable the signup button
        });

    }

    private void navigateToLogin() {
        finish();
    }

    private void showRegistrationSuccess() {
        Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}