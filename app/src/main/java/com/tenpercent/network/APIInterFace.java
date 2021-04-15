package com.tenpercent.network;


import com.tenpercent.pojo.APIResponse;

import com.tenpercent.pojo.Categories;
import com.tenpercent.pojo.ModelsOrders;
import com.tenpercent.pojo.Products;

import com.tenpercent.pojo.loginpojo.EditeModel;
import com.tenpercent.pojo.loginpojo.SuccessRsponse;

import org.json.JSONArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;

import retrofit2.http.POST;
import retrofit2.http.Path;


public interface APIInterFace {
    @FormUrlEncoded
    @POST("api/login?")
    Call<SuccessRsponse.loginRespons> createUser(@Field("phone") String phone,
                                                 @Field("device_token") String token
                                                , @Field("password") String password);


    @FormUrlEncoded
    @POST("api/register?")
    Call<SuccessRsponse.loginregister> createUserReigster(@Field("phone") String phone,
                                                          @Field("password") String password
                                                        , @Field("device_token") String device_token
                                                         , @Field("name") String name,
                                                          @Field("email") String email);


    @GET("api/home")
    Call<APIResponse.HomeResponse> getHomeLists();

    @GET("api/categories")
    Call<List<Categories>> getCategories();


    @GET("api/category/products/{id}")
    Call<List<Products>> getProductsByCategory(
            @Path("id") String id);

    @FormUrlEncoded
    @POST("api/update-profile?")
    Call<EditeModel> updateProfile(
            @Header("Authorization") String token,
            @Field("name") String name,
            @Field("phone") String phone);


    @FormUrlEncoded
    @POST("api/add-order?")
    Call<Boolean> addOrders(
            @Header("Authorization") String token,
            @Field("total_cost") double name,
            @Field("cart") JSONArray carts);

    @GET("api/my-orders")
    Call<List<ModelsOrders>> getMyOrders(
            @Header("Authorization") String token);

    @FormUrlEncoded
    @POST("api/search?")
    Call<List<Products>> searshProduct(
            @Header("Authorization") String token,
            @Field("search") String searsh);
}
