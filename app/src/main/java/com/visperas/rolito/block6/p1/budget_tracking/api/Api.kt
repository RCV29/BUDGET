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
import retrofit2.http.Path


interface Api {
   @POST("/api/people")
   fun registration(@Body registerRequest: RegisterRequest):Call<RegisterResponse>

   @POST("/api/login-retrofit")
   fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

   @GET("/api/get-expense")
   fun getExpense(@Header("Authorization")token: String): Call<ExpenseResponse>

   @POST("/api/post-expense")
   fun postExpense(@Header("Authorization") token: String, @Body expense: Expense): Call<DefaultResponse>

   @PUT("/api/update-expense/{expense}")
   fun updateExpense(
      @Header("Authorization") token: String, // Add Authorization header
      @Path("expense") expenseId: Int,
      @Body updatedExpense: Expense
   ): Call<DefaultResponse>

   @DELETE("/api/delete-expense/{expense}")
   fun deleteExpense(
      @Header("Authorization") authorization: String,
      @Path("expense") expenseId: Int
   ): Call<DefaultResponse>

   @GET("/api/get-saving")
   fun getSaving(@Header("Authorization")token: String): Call<SavingResponse>

   @POST("/api/post-saving")
   fun postSaving(@Header("Authorization") token: String, @Body saving: Saving): Call<DefaultResponse>

   @PUT("/api/update-saving/{saving}")
   fun updateSaving(
      @Header("Authorization") token: String, // Add Authorization header
      @Path("saving") savingId: Int,
      @Body updatedSaving: Saving
   ): Call<DefaultResponse>

   @DELETE("/api/delete-saving/{saving}")
   fun deleteSaving(
      @Header("Authorization") authorization: String,
      @Path("saving") savingId: Int
   ): Call<DefaultResponse>



}
