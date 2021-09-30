package com.flowz.sixtjobapp.data.remote.dto


import com.flowz.sixtjobapp.domain.model.Car
import com.google.gson.annotations.SerializedName

data class CarDto(
    val carImageUrl: String,
    val color: String,
    val fuelLevel: Double,
    val fuelType: String,
    val group: String,
    val id: String,
    val innerCleanliness: String,
    val latitude: Double,
    val licensePlate: String,
    val longitude: Double,
    val make: String,
    val modelIdentifier: String,
    val modelName: String,
    val name: String,
    val series: String,
    val transmission: String
)

fun CarDto.toCar() : Car{

    return Car(
    carImageUrl = carImageUrl,
    color= color,
    fuelType= fuelType,
    group = group,
    id= id,
    innerCleanliness= innerCleanliness,
    latitude = latitude,
    licensePlate = licensePlate,
    longitude = longitude,
    make = make,
    modelName = modelName,
    name = name,
    series = series,
    )
}