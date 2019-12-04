package com.example.rental.data.api

import com.example.rental.data.model.Car
import io.reactivex.Single
import retrofit2.http.GET

interface CarsService {

    @GET("cars")
    fun getAvailableCars(): Single<List<Car>>

}