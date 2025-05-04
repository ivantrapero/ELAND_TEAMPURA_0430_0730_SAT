package com.trapero.cchoice.viewmodels;

import android.util.Patterns;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.gson.JsonObject;
import com.trapero.cchoice.api.ApiService;
import com.trapero.cchoice.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {

    private final MutableLiveData<String> fullName = new MutableLiveData<>();
    private final MutableLiveData<String> email = new MutableLiveData<>();
    private final MutableLiveData<String> password = new MutableLiveData<>();
    private final MutableLiveData<String> confirmPassword = new MutableLiveData<>();
    private final MutableLiveData<String> address = new MutableLiveData<>();
    private final MutableLiveData<String> contactNumber = new MutableLiveData<>();

    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> navigateToLogin = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> registrationSuccess = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isRegistering = new MutableLiveData<>(false); // To show loading state

    public LiveData<String> getFullName() {
        return fullName;
    }

    public LiveData<String> getEmail() {
        return email;
    }

    public LiveData<String> getPassword() {
        return password;
    }

    public LiveData<String> getConfirmPassword() {
        return confirmPassword;
    }

    public LiveData<String> getAddress() {
        return address;
    }

    public LiveData<String> getContactNumber() {
        return contactNumber;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getNavigateToLogin() {
        return navigateToLogin;
    }

    public LiveData<Boolean> getRegistrationSuccess() {
        return registrationSuccess;
    }

    public LiveData<Boolean> getIsRegistering() {
        return isRegistering;
    }

    public void onSignUpClicked() {
        String fullNameValue = fullName.getValue();
        String emailValue = email.getValue();
        String passwordValue = password.getValue();
        String confirmPasswordValue = confirmPassword.getValue();
        String addressValue = address.getValue();
        String contactNumberValue = contactNumber.getValue();

        if (fullNameValue == null || fullNameValue.trim().isEmpty()) {
            errorMessage.setValue("Please enter your full name.");
            return;
        }
        if (emailValue == null || !Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
            errorMessage.setValue("Please enter a valid email address.");
            return;
        }
        if (passwordValue == null || passwordValue.length() < 6) {
            errorMessage.setValue("Password must be at least 6 characters long.");
            return;
        }
        if (confirmPasswordValue == null || !confirmPasswordValue.equals(passwordValue)) {
            errorMessage.setValue("Passwords do not match.");
            return;
        }

        isRegistering.setValue(true);
        JsonObject registrationData = new JsonObject();
        registrationData.addProperty("fullName", fullNameValue);
        registrationData.addProperty("email", emailValue);
        registrationData.addProperty("password", passwordValue);
        registrationData.addProperty("password_confirmation", confirmPasswordValue);
        registrationData.addProperty("address", addressValue);
        registrationData.addProperty("contactNumber", contactNumberValue);

        ApiService apiService = RetrofitClient.getApiService();
        Call<JsonObject> call = apiService.registerUser(registrationData);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                isRegistering.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    // Handle successful registration response
                    String message = response.body().get("message").getAsString();
                    registrationSuccess.setValue(true);
                } else {
                    // Handle unsuccessful registration response (e.g., validation errors)
                    if (response.errorBody() != null) {
                        try {
                            String errorString = response.errorBody().string();
                            // You might want to parse this JSON error to display specific messages
                            errorMessage.setValue("Registration failed: " + errorString);
                        } catch (Exception e) {
                            errorMessage.setValue("Registration failed: An unknown error occurred.");
                        }
                    } else {
                        errorMessage.setValue("Registration failed: No error body.");
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                isRegistering.setValue(false);
                errorMessage.setValue("Network error: " + t.getMessage());
            }
        });
    }

    public void onLoginClicked() {
        navigateToLogin.setValue(true);
    }

    public void doneNavigatingToLogin() {
        navigateToLogin.setValue(false);
    }

    public void doneRegistering() {
        registrationSuccess.setValue(false);
    }

    public void doneShowingError() {
        errorMessage.setValue(null);
    }
}