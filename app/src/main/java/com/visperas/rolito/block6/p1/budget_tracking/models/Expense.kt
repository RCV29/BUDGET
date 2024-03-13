package com.visperas.rolito.block6.p1.budget_tracking.models

data class Expense(
    val id: Int,
    val user_id: Int,
    val exp: String,
    val price: String,
    val date: String,
    val created_at: String,
    val updated_at: String
)