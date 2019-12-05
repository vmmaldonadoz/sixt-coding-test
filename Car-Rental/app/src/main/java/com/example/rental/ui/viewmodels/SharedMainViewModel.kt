package com.example.rental.ui.viewmodels

import com.example.rental.data.controller.CarInformationController
import com.example.rental.data.room.Car
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SharedMainViewModel @Inject constructor(
    val carInformationController: CarInformationController
) : BaseViewModel() {

    fun getAvailableCars(): Observable<List<Car>> {
        return carInformationController.getAvailableCars()
    }

    override fun onResume() {
        super.onResume()
        updateAvailableCars()
    }

    private fun updateAvailableCars() {
        compositeDisposable.add(
            carInformationController.updateAvailableCars()
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

}