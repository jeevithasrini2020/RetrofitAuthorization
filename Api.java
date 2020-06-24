package com.example.proj3.Interface;

import com.example.proj3.Model.Profile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {
    String BASE_URL = "http://178.128.151.220/funnzy/public/api/user/";
    @POST("get_all_user_details")
    Call<List<Profile>> getHeroes();
}
