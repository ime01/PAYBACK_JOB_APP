package com.flowz.sixtjobapp.domain.usecases

import com.flowz.sixtjobapp.data.remote.dto.toCar
import com.flowz.sixtjobapp.domain.model.Car
import com.flowz.sixtjobapp.domain.repository.CarsRepository
import com.plcoding.cryptocurrencyappyt.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCarsUseCase @Inject constructor (private val repository: CarsRepository) {

//    trigger the api call now...

    operator fun invoke (): Flow<Resource<List<Car>>> = flow {

        try {
            emit(Resource.Loading<List<Car>>())

            val cars = repository.getCars().map { it.toCar() }

            emit(Resource.Success(cars))

        }catch (e:HttpException){

            emit(Resource.Error<List<Car>>(e.localizedMessage ?: "An unexpected error occurred"))

        }catch (e: IOException){

            emit(Resource.Error<List<Car>>("Couldn't reach the server, Check your internet connection and try again"))
        }
    }
}