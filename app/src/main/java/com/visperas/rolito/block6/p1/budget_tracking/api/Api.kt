package com.visperas.rolito.block6.p1.budget_tracking.api

import com.visperas.rolito.block6.p1.budget_tracking.models.DefaultResponse
import com.visperas.rolito.block6.p1.budget_tracking.models.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {

   @FormUrlEncoded
   @POST("registration.post")
   fun registration(
      @Field("name") name:String,
      @Field("email") email:String,
      @Field("password") password:String
   ):Call<DefaultResponse>

   @FormUrlEncoded
   @POST("login.post")
   fun login(
      @Field("email") email:String,
      @Field("password") password:String
   ):Call<LoginResponse>
}