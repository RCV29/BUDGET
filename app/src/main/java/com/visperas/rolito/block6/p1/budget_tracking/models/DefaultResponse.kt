package com.visperas.rolito.block6.p1.budget_tracking.models

import com.google.gson.annotations.SerializedName

data class DefaultResponse(
    @SerializedName("error") val error: Boolean,
    @SerializedName("message") val message: String,

)
