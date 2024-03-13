package com.visperas.rolito.block6.p1.budget_tracking.api

import com.visperas.rolito.block6.p1.budget_tracking.models.DefaultResponse
import com.visperas.rolito.block6.p1.budget_tracking.models.Expense
import com.visperas.rolito.block6.p1.budget_tracking.models.ExpenseResponse
import com.visperas.rolito.block6.p1.budget_tracking.models.LoginRequest
import com.visperas.rolito.block6.p1.budget_tracking.models.LoginResponse
import com.visperas.rolito.block6.p1.budget_tracking.models.RegisterRequest
import com.visperas.rolito.block6.p1.budget_tracking.models.RegisterResponse
import com.visperas.rolito.block6.p1.budget_tracking.models.Saving
import com.visperas.rolito.block6.p1.budget_tracking.models.SavingResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT


interface Api {
   @POST("/api/people")
   fun registration(@Body registerRequest: RegisterRequest):Call<RegisterResponse>

   @POST("/api/login-retrofit")
   fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

   @GET("/api/get-expense")
   fun getExpense(@Header("Authorization")token: String): Call<ExpenseResponse>

   @POST("/api/post-expense")
   fun postExpense(@Header("Authorization") token: String, @Body expense: Expense): Call<DefaultResponse>

   @PUT("/update-expense")
   fun updateExpense(@Body expense: Expense): Call<Void>

   @DELETE("/delete-expense")
   fun deleteExpense(): Call<Void>

   @GET("/api/get-saving")
   fun getSaving(@Header("Authorization")token: String): Call<SavingResponse>

   @POST("/api/post-saving")
   fun postSaving(@Header("Authorization") token: String, @Body saving: Saving): Call<DefaultResponse>

}