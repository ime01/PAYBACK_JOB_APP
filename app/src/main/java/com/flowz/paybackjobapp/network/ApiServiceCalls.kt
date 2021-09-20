package com.flowz.paybackjobapp.network

import com.flowz.agromailjobtask.utils.Constants
import com.flowz.paybackjobapp.BuildConfig
import com.flowz.paybackjobapp.models.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceCalls {

    @GET(Constants.END_POINT)
    suspend fun getImages(@Query("key") key:String, @Query("q") query:String, @Query("image_type") imageType:String) : ImageResponse
}