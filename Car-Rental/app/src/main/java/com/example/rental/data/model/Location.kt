package com.example.rental.data.model

import com.example.rental.data.room.Car
import com.google.android.gms.maps.model.LatLng

data class Location(
    val latitude: Double,
    val longitude: Double
)

fun Car.getLocation(): Location {
    return Location(
        latitude.toDouble(),
        longitude.toDouble()
    )
}

fun Location.toLatLng() = LatLng(latitude, longitude)