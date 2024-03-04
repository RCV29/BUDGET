package com.visperas.rolito.block6.p1.budget_tracking.api

import com.visperas.rolito.block6.p1.budget_tracking.models.DefaultResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface Api {

   @FormUrlEncoded
   @Headers("Accept: application/json")
   @POST("registration")
   fun registrationUser(
      @Field("name") name:String,
      @Field("email") email:String,
      @Field("password") password:String
   ):Call<DefaultResponse>

   @FormUrlEncoded
   @Headers("Accept: application/json")
   @POST("login")
   fun loginUser(
      @Field("email") email:String,
      @Field("password") password:String
   ):Call<DefaultResponse>
}