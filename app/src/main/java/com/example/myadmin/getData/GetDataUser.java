package com.example.myadmin.getData;

import com.example.myadmin.model.ResultLogin;
import com.example.myadmin.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface GetDataUser {

    @GET("login")
    Call<ArrayList<ResultLogin>>getLogin(
            @Query("username") String username,
            @Query("password") String pass
    );

    @GET("userInfoById")
    Call<ArrayList<User>> getInfoUser(
            @Query("userID") int userID
    );

    @GET("changePassword")
    Call<ArrayList<ResultLogin>> getChangePass(
            @Query("userID") int userID,
            @Query("oldPass") String oldPass,
            @Query("newPass") String newPass
    );

    @POST("signup")
    Call<ArrayList<ResultLogin>> postSingUp(
            @Query("name") String name,
            @Query("email") String email,
            @Query("phone") String phone,
            @Query("username") String username,
            @Query("password") String password,
            @Query("address") String address
    );
    @POST("signup")
    Call<ArrayList<ResultLogin>> postBodySingUp(@Body User user);
    @PUT("updateUser")
    Call<ArrayList<ResultLogin>> putupdateUser(
            @Query("userId") int userId,
            @Query("name") String name,
            @Query("email") String email,
            @Query("phone") String phone,
            @Query("address") String address,
            @Query("avatar") String avatar
    );
    @PUT("updateUser")
    Call<ArrayList<ResultLogin>> putupdateUserBody(@Body User user);
}
