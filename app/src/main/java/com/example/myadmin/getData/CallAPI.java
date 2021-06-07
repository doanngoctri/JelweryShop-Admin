package com.example.myadmin.getData;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.myadmin.API.API.BASE_API;

public class CallAPI {
    public  static volatile CallAPI callAPI= null;
    private  static Retrofit retrofit;


    public static Retrofit getRetrofitInstance() {
         HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

         OkHttpClient client = new OkHttpClient
                 .Builder()
                 .connectTimeout(1, TimeUnit.MINUTES) // Change it as per your requirement
                 .readTimeout(1, TimeUnit.MINUTES)// Change it as per your requirement
                 .writeTimeout(1, TimeUnit.MINUTES)
                 .addInterceptor(interceptor)
                 .build();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_API)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
