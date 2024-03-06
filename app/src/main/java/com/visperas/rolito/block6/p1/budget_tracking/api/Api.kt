package com.visperas.rolito.block6.p1.budget_tracking.api

import com.visperas.rolito.block6.p1.budget_tracking.models.LoginRequest
import com.visperas.rolito.block6.p1.budget_tracking.models.LoginResponse
import com.visperas.rolito.block6.p1.budget_tracking.models.RegisterRequest
import com.visperas.rolito.block6.p1.budget_tracking.models.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {
   @POST("/api/people")
   fun registration(@Body registerRequest: RegisterRequest):Call<RegisterResponse>

   @POST("/api/login-retrofit")
   fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}