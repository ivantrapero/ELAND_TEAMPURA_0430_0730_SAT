package com.trapero.cchoice.viewmodels;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.trapero.cchoice.api.ApiService;
import com.trapero.cchoice.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<String> email = new MutableLiveData<>();
    private MutableLiveData<String> password = new MutableLiveData<>();

    private MutableLiveData<Boolean> navigateToRegister = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>(false);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoggingIn = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> navigateToForgotPassword = new MutableLiveData<>(false);

    public LiveData<String> getEmail() {
        return email;
    }

    public LiveData<String> getPassword() {
        return password;
    }

    public LiveData<Boolean> getNavigateToRegister() {
        return navigateToRegister;
    }

    public LiveData<Boolean> getLoginSuccess() {
        return loginSuccess;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getIsLoggingIn() {
        return isLoggingIn;
    }

    public LiveData<Boolean> getNavigateToForgotPassword() {
        return navigateToForgotPassword;
    }

    public void onLoginClicked() {
        String emailValue = email.getValue();
        String passwordValue = password.getValue();

        if (emailValue == null || emailValue.trim().isEmpty() || passwordValue == null || passwordValue.isEmpty()) {
            errorMessage.setValue("Please enter email and password.");
            return;
        }

        isLoggingIn.setValue(true);

        // TEMPORARILY DISABLE API CALL AND SIMULATE SUCCESS
        Log.d("LoginViewModel", "Login API call temporarily disabled. Simulating success.");
        // Simulate a successful login after a short delay (optional, for visual feedback)
        new android.os.Handler().postDelayed(() -> {
            isLoggingIn.setValue(false);
            loginSuccess.setValue(true);
        }, 1000); // Simulate a 1-second delay

        JsonObject loginData = new JsonObject();
        loginData.addProperty("email", emailValue);
        loginData.addProperty("password", passwordValue);

        ApiService apiService = RetrofitClient.getApiService();
        Call<JsonObject> call = apiService.loginUser(loginData);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                isLoggingIn.setValue(false);
                Log.d("LoginViewModel", "Login API Response Code: " + response.code());
                Log.d("LoginViewModel", "Login API Response Raw: " + response.toString());

                if (response.isSuccessful() && response.body() != null) {
                    JsonObject responseBody = response.body();
                    Log.d("LoginViewModel", "Login API Response Body: " + responseBody.toString());

                    if (responseBody.has("success") && responseBody.get("success").getAsBoolean()) {
                        Log.d("LoginViewModel", "Login successful according to API response.");
                        loginSuccess.setValue(true);
                        if (responseBody.has("token")) {
                            String token = responseBody.get("token").getAsString();
                            Log.d("LoginViewModel", "Login successful, token: " + token);
                            // TODO: Store the token securely
                        } else {
                            Log.w("LoginViewModel", "Login successful, but no token received.");
                        }
                        if (responseBody.has("user")) {
                            Log.d("LoginViewModel", "User data received: " + responseBody.getAsJsonObject("user").toString());
                            // TODO: Store or process user data
                        }
                    } else {
                        Log.w("LoginViewModel", "Login failed according to API response.");
                        String message = "Login failed";
                        if (responseBody.has("message")) {
                            message = "Login failed: " + responseBody.get("message").getAsString();
                        } else if (responseBody.has("error")) {
                            message = "Login failed: " + responseBody.get("error").getAsString();
                        }
                        errorMessage.setValue(message);
                        Log.e("LoginViewModel", "Login API returned failure: " + responseBody.toString());
                    }
                } else {
                    Log.e("LoginViewModel", "Login HTTP error response. Code: " + response.code() + ", Message: " + response.message());
                    String errorMessageValue = "Login failed due to a server error.";
                    if (response.errorBody() != null) {
                        try {
                            String errorString = response.errorBody().string();
                            Log.e("LoginViewModel", "Login HTTP error body: " + errorString);
                            try {
                                JsonObject errorJson = JsonParser.parseString(errorString).getAsJsonObject();
                                if (errorJson.has("message")) {
                                    errorMessageValue = "Login failed: " + errorJson.get("message").getAsString();
                                } else if (errorJson.has("error")) {
                                    errorMessageValue = "Login failed: " + errorJson.get("error").getAsString();
                                } else {
                                    errorMessageValue = "Login failed: " + errorString;
                                }
                            } catch (Exception e) {
                                errorMessageValue = "Login failed: Could not parse error response.";
                                Log.e("LoginViewModel", "Error parsing error body: " + e.getMessage());
                            }
                        } catch (Exception e) {
                            errorMessageValue = "Login failed: Could not read error response.";
                            Log.e("LoginViewModel", "Error reading error body: " + e.getMessage());
                        }
                    }
                    errorMessage.setValue(errorMessageValue);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                isLoggingIn.setValue(false);
                errorMessage.setValue("Network error: " + t.getMessage());
                Log.e("LoginViewModel", "Login network error: " + t.getMessage());
            }
        });

    }

    public void onSignUpClicked() {
        navigateToRegister.setValue(true);
    }

    public void onForgotPasswordClicked() {
        navigateToForgotPassword.setValue(true);
    }

    public void doneNavigatingToRegister() {
        navigateToRegister.setValue(false);
    }

    public void doneLoggingIn() {
        loginSuccess.setValue(false);
    }

    public void doneShowingError() {
        errorMessage.setValue(null);
    }

    public void doneNavigatingToForgotPassword() {
        navigateToForgotPassword.setValue(false);
    }
}