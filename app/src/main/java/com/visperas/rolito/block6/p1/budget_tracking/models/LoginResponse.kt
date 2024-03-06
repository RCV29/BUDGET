package com.visperas.rolito.block6.p1.budget_tracking.models

data class LoginResponse(
    val id: Int,
    val email: String,
    val password: String,
    val email_verified_at: String,
    val created_at:String,
    val updated_at:String
)
