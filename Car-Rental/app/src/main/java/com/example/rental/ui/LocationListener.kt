package com.example.rental.ui

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.rental.data.model.Location
import com.google.android.gms.location.*
import java.util.concurrent.TimeUnit

class LocationListener(
    private val context: Activity,
    private val lifecycle: Lifecycle,
    private val callback: (Location) -> Unit
) : LifecycleObserver {

    private val MY_PERMISSIONS_REQUEST_LOCATION: Int = 0x1

    private val locationCallback: LocationCallback by lazy {
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                callback(
                    Location(
                        locationResult.lastLocation.latitude,
                        locationResult.lastLocation.longitude
                    )
                )
            }
        }
    }

    private val locationRequest by lazy {
        LocationRequest().apply {
            interval = TimeUnit.SECONDS.toMillis(10)
            fastestInterval = TimeUnit.SECONDS.toMillis(5)
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            startLocationUpdates()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun startLocationUpdates() {
        if (shouldRequestForLocationPermission()) {
            ActivityCompat.requestPermissions(
                context,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_LOCATION
            )
        } else {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
            )
        }
    }

    private fun shouldRequestForLocationPermission(): Boolean {
        return checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION
            && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            start()
        }
    }
}