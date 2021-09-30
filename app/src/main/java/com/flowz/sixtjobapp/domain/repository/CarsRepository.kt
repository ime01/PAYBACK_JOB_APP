package com.flowz.sixtjobapp.domain.repository

import com.flowz.sixtjobapp.data.remote.dto.CarDto
import com.flowz.sixtjobapp.data.remote.dto.CarsDto
import com.flowz.sixtjobapp.domain.model.Car
import com.flowz.sixtjobapp.domain.model.Cars

interface CarsRepository {

    suspend fun getCars(): List<CarDto>

}