package com.flowz.sixtjobapp.data.remote

import com.flowz.sixtjobapp.data.remote.dto.CarDto
import com.flowz.sixtjobapp.data.remote.dto.CarsDto
import com.flowz.sixtjobapp.domain.model.Car
import retrofit2.http.GET

interface CarsApi {

    @GET("/codingtask/cars")
    suspend fun getCars() : List<CarDto>
}