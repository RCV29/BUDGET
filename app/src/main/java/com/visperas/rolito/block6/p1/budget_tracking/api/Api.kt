package com.visperas.rolito.block6.p1.budget_tracking.api

import com.visperas.rolito.block6.p1.budget_tracking.models.DefaultResponse
import com.visperas.rolito.block6.p1.budget_tracking.models.LoginRequest
import com.visperas.rolito.block6.p1.budget_tracking.models.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
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

   @POST("/api/people")
   fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}