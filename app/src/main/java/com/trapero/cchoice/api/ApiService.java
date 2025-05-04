package com.trapero.cchoice.api;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("register")
    Call<JsonObject> registerUser(@Body JsonObject registrationData);
    @POST("login")
    Call<JsonObject> loginUser(@Body JsonObject loginData);

}