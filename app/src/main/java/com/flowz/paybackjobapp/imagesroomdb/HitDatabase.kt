package com.flowz.drinkcocktails.drinkroomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flowz.paybackjobapp.models.Hit

@Database(entities = [Hit::class], version = 1, exportSchema = false)
abstract class HitDatabase : RoomDatabase() {

    abstract fun hitsDao(): HitsDao
}

