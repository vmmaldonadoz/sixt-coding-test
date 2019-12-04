package com.example.rental.data.mappers

import com.example.rental.data.api.CarResponse
import com.example.rental.data.room.Car
import javax.inject.Inject

class CarInformationMapper @Inject constructor() {

    fun mapListOfCars(response: List<CarResponse>): List<Car> {
        return response.map { element -> mapToCar(element) }
    }

    fun mapToCar(response: CarResponse): Car {
        return Car(
            id = response.id,
            modelIdentifier = response.modelIdentifier,
            modelName = response.modelName,
            name = response.name,
            make = response.make,
            group = response.group,
            color = response.color,
            series = response.series,
            fuelType = response.fuelType,
            fuelLevel = response.fuelLevel,
            transmission = response.transmission,
            licensePlate = response.licensePlate,
            latitude = response.latitude,
            longitude = response.longitude,
            innerCleanliness = response.innerCleanliness,
            carImageUrl = response.carImageUrl
        )
    }
}