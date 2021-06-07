package com.example.myadmin.getData;

import com.example.myadmin.model.Orders;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface GetDataOrders {
    @GET("allOrdersOfUser")
    Call<ArrayList<Orders>> getallOrdersUser(
            @Query("userId") int userId
    );


    @PUT("order")
    void cfOrder(
            @Query("orderId") int orderId
    );

    @GET("allOrders")
    Call<ArrayList<Orders>> getallOrders(
    );

    @PUT("statusOrder")
    Call<Integer> statusOrder(
            @Query("Id") int orderId
    );
    @PUT("updateOrder")
    Call<Integer> updateOrder(
            @Query("Id") int orderId
    );
    @PUT("CancelOrder")
    Call<Integer> CancelAnOrder(
            @Query("Id") int orderId
    );
}
