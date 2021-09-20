package com.flowz.paybackjobapp.repository

import com.flowz.agromailjobtask.utils.Constants
import com.flowz.drinkcocktails.drinkroomdb.HitsDao
import com.flowz.paybackjobapp.BuildConfig
import com.flowz.paybackjobapp.models.Hit
import com.flowz.paybackjobapp.models.ImageResponse
import com.flowz.paybackjobapp.network.ApiServiceCalls
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ImageRepository @Inject constructor( private val apiClient: ApiServiceCalls, private val dbReference : HitsDao) {

    val hitsFromDb = dbReference.getHits()


    suspend fun fetchAllImages( searchQuery: String): ImageResponse{
        return apiClient.getImages(BuildConfig.GMB_KEY , searchQuery, Constants.IMAGETYPE)
    }

    suspend fun insertListOfHitsIntoDb(hits: List<Hit>){
        dbReference.insertHits(hits)
    }

    suspend fun clearAllHitsInDb(){
        dbReference.clearAll()
    }

}