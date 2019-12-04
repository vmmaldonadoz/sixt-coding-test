package com.example.rental.data.model

import com.google.gson.annotations.SerializedName

data class Car(
    @SerializedName("id") val id: String,
    @SerializedName("modelIdentifier") val modelIdentifier: String,
    @SerializedName("modelName") val modelName: String,
    @SerializedName("name") val name: String,
    @SerializedName("make") val make: String,
    @SerializedName("group") val group: String,
    @SerializedName("color") val color: String,
    @SerializedName("series") val series: String,
    @SerializedName("fuelType") val fuelType: String,
    @SerializedName("fuelLevel") val fuelLevel: Float,
    @SerializedName("transmission") val transmission: String,
    @SerializedName("licensePlate") val licensePlate: String,
    @SerializedName("latitude") val latitude: Float,
    @SerializedName("longitude") val longitude: Float,
    @SerializedName("innerCleanliness") val innerCleanliness: String,
    @SerializedName("carImageUrl") val carImageUrl: String
)