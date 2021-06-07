package com.example.myadmin.getData;

import com.example.myadmin.model.Product;
import com.example.myadmin.model.SanPham;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface GetData_Product {

    @GET("allProducts")
    Call<ArrayList<SanPham>> getAllSanPham() ;

    @GET("allProsOfCart")
    Call<ArrayList<SanPham>> getProductCart(
        @Query("userId") int idUser
    );

    @GET("allProsOfProGroup")
    Call<List<SanPham>> getProductGroup(
            @Query("proGroupID") int idProductGroup
    );

    @GET("allSalePros")
    Call<List<SanPham>> getAllProductSale() ;

    @GET("proDetail")
    Call<ArrayList<SanPham>> getProduct(
            @Query("proId") int idProduct
    );
    @GET("orderItemOfOrderDetail")
    Call<ArrayList<SanPham>> getDetailOrder(
            @Query("orderid") int id
    ) ;
    @PUT("Product")
    Call<Integer> putProduct(
            @Body Product product
    );
    @POST("newProduct")
    Call<Integer> addProduct(
            @Body Product product
    );
    @GET("Statistic")
    Call<ArrayList<SanPham>> getStatic();
    @GET("Statistic")
    Call<ArrayList<SanPham>> getStaticByTime(
            @Query("from") String from,
            @Query("to") String to
    );
}
