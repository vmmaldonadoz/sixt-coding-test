package com.example.rental.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Car(
    @PrimaryKey
    val id: String,
    val modelIdentifier: String,
    val modelName: String,
    val name: String,
    val make: String,
    val group: String,
    val color: String,
    val series: String,
    val fuelType: String,
    val fuelLevel: Float,
    val transmission: String,
    val licensePlate: String,
    val latitude: Float,
    val longitude: Float,
    val innerCleanliness: String,
    val carImageUrl: String
)