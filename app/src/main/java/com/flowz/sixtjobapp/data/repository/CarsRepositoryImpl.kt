package com.flowz.sixtjobapp.data.repository

import com.flowz.sixtjobapp.data.remote.CarsApi
import com.flowz.sixtjobapp.data.remote.dto.CarDto
import com.flowz.sixtjobapp.data.remote.dto.CarsDto
import com.flowz.sixtjobapp.domain.model.Car
import com.flowz.sixtjobapp.domain.repository.CarsRepository
import javax.inject.Inject

class CarsRepositoryImpl @Inject constructor (private val api: CarsApi):CarsRepository{

    override suspend fun getCars(): List<CarDto> {
       return api.getCars()
    }
}