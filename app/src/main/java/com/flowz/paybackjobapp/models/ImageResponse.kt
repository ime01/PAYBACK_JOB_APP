package com.flowz.paybackjobapp.models


import com.google.gson.annotations.SerializedName

data class ImageResponse(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)