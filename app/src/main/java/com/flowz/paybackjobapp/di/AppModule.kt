package com.flowz.drinkcocktails.di

import android.content.Context
import androidx.room.Room
import com.flowz.agromailjobtask.utils.Constants
import com.flowz.paybackjobapp.network.ApiServiceCalls
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    val okHttpClient = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()


    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit): ApiServiceCalls =
        retrofit.create(ApiServiceCalls::class.java)


//    @Provides
//    @Singleton
//    fun providesDrinkDatabase(@ApplicationContext app: Context) =
//        Room.databaseBuilder(app, DrinkDatabase::class.java, "cocktailDrinks.db").build()
//
//
//    @Provides
//    @Singleton
//    fun providesDrinksDao (db: DrinkDatabase) = db.drinkDao()
//
//    @Singleton
//    @Provides
//    fun provideDefaultDrinkRepository(
//        dao: DrinkDao,
//        api: ApiServiceCalls
//    ) = DrinksCocktailsRepositoryForTesting( api, dao) as TdrinkRepository
    
}