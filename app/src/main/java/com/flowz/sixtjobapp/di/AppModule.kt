package com.plcoding.cryptocurrencyappyt.di


import com.flowz.sixtjobapp.data.remote.CarsApi
import com.flowz.sixtjobapp.data.repository.CarsRepositoryImpl
import com.flowz.sixtjobapp.domain.repository.CarsRepository
import com.plcoding.cryptocurrencyappyt.common.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providesCarsApi(): CarsApi{
        return  Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CarsApi::class.java)
    }

    @Provides
    @Singleton
    fun providesCarsRepository(api: CarsApi): CarsRepository{
        return  CarsRepositoryImpl(api)
    }


}