package com.visperas.rolito.block6.p1.budget_tracking.models

data class Saving(
    val id: Int,
    val user_id: Int,
    val amount: String,
    val date: String,
    val created_at: String,
    val updated_at: String
)
