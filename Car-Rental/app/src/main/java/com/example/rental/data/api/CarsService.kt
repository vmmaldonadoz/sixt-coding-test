package com.example.rental.data.api

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET

interface CarsService {

    @GET("cars")
    fun getAvailableCars(): Observable<List<CarResponse>>

}