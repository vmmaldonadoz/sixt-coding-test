package com.example.rental.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.rental.R
import com.example.rental.ui.LocationListener
import com.example.rental.ui.maps.MapViewModel
import com.example.rental.ui.viewmodels.SharedMainViewModel
import com.example.rental.utils.isNotNullOrBlank
import com.example.rental.utils.tryOrPrintException
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var locationListener: LocationListener

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: SharedMainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()

        setupBottomNavigation()

        setupLocationListener()
    }

    private fun setupBottomNavigation() {
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders
            .of(this, viewModelFactory)
            .get(SharedMainViewModel::class.java)

        lifecycle.addObserver(viewModel)
    }

    private val mapFragment = SupportMapFragment.newInstance().apply {
        getMapAsync { googleMap ->
            googleMap.setOnMarkerClickListener { marker ->
                if (marker.title.isNotNullOrBlank()) {
                    marker.showInfoWindow()
                    true
                } else {
                    false
                }
            }
            tryOrPrintException {
                googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                        this@MainActivity,
                        R.raw.car_rental_style
                    )
                )
            }
            mapViewModel = MapViewModel(googleMap, viewModel).also { mapViewModel ->
                lifecycle.addObserver(mapViewModel)
                mapViewModel.connect()
            }
        }
    }

    private var mapViewModel: MapViewModel? = null

    private fun setupLocationListener() {
        locationListener = LocationListener(this, lifecycle) { location ->
            mapViewModel?.updateUserLocation(location)
        }
    }

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    true
                }
                R.id.navigation_map -> {
                    loadFragment(mapFragment)
                    true
                }
                R.id.navigation_information -> {
                    true
                }
                else -> false
            }
        }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commitAllowingStateLoss()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        locationListener.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
        )
    }
}
