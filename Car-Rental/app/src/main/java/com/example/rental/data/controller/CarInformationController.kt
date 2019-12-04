package com.example.rental.data.controller

import com.example.rental.data.api.CarsService
import com.example.rental.data.mappers.CarInformationMapper
import com.example.rental.data.room.Car
import com.example.rental.data.room.CarInformationDao
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CarInformationController @Inject constructor(
    val carsService: CarsService,
    val carInformationDao: CarInformationDao,
    val carInformationMapper: CarInformationMapper
) {

    fun updateAvailableCars(): Completable? {
        return carsService.getAvailableCars()
            .map { response -> carInformationMapper.mapListOfCars(response) }
            .flatMapCompletable { carInformationDao.updateCarInformation(it) }
    }

    fun getAvailableCars(): Single<List<Car>> {
        return carInformationDao.getAll()
    }

    fun getDetailForCar(carId: String): Maybe<Car> {
        return carInformationDao.getCarById(carId)
    }
}