package com.example.rental.ui.maps

import com.example.rental.R
import com.example.rental.data.model.Location
import com.example.rental.data.model.getLocation
import com.example.rental.data.model.toLatLng
import com.example.rental.data.room.Car
import com.example.rental.ui.viewmodels.BaseViewModel
import com.example.rental.ui.viewmodels.SharedMainViewModel
import com.example.rental.utils.subscribeOnIO
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.maps.android.MarkerManager
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class MapViewModel(
    private val googleMap: GoogleMap,
    private val viewModel: SharedMainViewModel
) : BaseViewModel() {

    private val lastKnownLocation: BehaviorSubject<LatLng> = BehaviorSubject.create()

    private var userMarker: Marker? = null
    private val markerManager: MarkerManager = MarkerManager(googleMap)
    private val markerCollection = markerManager.newCollection()

    override fun onResume() {
        super.onResume()
        connect()
    }

    fun updateUserLocation(location: Location) {
        lastKnownLocation.onNext(location.toLatLng())
    }

    fun connect() {
        compositeDisposable.addAll(
            lastKnownLocation
                .subscribeOnIO()
                .subscribe(
                    ::handleUserLocation,
                    Throwable::printStackTrace
                ),
            viewModel.getAvailableCars()
                .subscribeOnIO()
                .subscribeBy(
                    onNext = { cars -> showAvailableCars(cars) },
                    onError = { error -> error.printStackTrace() }
                ),
            Observable.interval(3, TimeUnit.SECONDS)
                .timeInterval()
                .subscribeOnIO()
                .subscribeBy(
                    onNext = { updateCamera() },
                    onError = { error -> error.printStackTrace() }
                )
        )
    }

    private fun updateCamera() {
        val referencePoints = mutableListOf<LatLng>()

        markerCollection.markers.forEach { referencePoints.add(it.position) }

        if (referencePoints.isNotEmpty()) {
            val builder = LatLngBounds.Builder()
            referencePoints.forEach { builder.include(it) }
            val latLngBounds = builder.build()

            val mapMargin = 30
            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(latLngBounds, mapMargin)
            googleMap.animateCamera(cameraUpdate, TimeUnit.SECONDS.toMillis(1).toInt(), null)
        }
    }

    private fun showAvailableCars(availableCars: List<Car>) {
        markerCollection.clear()
        availableCars.forEach { car ->
            val latLng = car.getLocation().toLatLng()
            markerCollection.addMarker(
                MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_information_black_24dp)).position(
                    latLng
                ).title(car.name)
            )
        }
        updateCamera()
    }

    private fun handleUserLocation(latLng: LatLng) {
        userMarker?.remove()
        userMarker = googleMap.addMarker(
            MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_black_24dp)).position(
                latLng
            ).title("")
        )
    }

}
