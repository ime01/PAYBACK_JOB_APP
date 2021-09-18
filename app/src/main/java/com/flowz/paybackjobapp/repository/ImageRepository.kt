package com.flowz.paybackjobapp.repository

import com.flowz.agromailjobtask.utils.Constants
import com.flowz.paybackjobapp.models.ImageResponse
import com.flowz.paybackjobapp.network.ApiServiceCalls
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ImageRepository @Inject constructor( private val apiClient: ApiServiceCalls) {

    suspend fun fetchAllImages( searchQuery: String): ImageResponse{
        return apiClient.getImages(Constants.KEY, searchQuery, Constants.IMAGETYPE)
    }

}