package com.flowz.drinkcocktails.drinkroomdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.flowz.paybackjobapp.models.Hit
import kotlinx.coroutines.flow.Flow

@Dao
interface HitsDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHits (hits: List<Hit>)


    @Query("SELECT * FROM images_table where id = id")
    fun getHits() : Flow<List<Hit>>

    @Query("SELECT * FROM images_table where id = id")
    fun getLocalHits() : LiveData<List<Hit>>

    @Query("DELETE FROM images_table")
    fun clearAll()

}