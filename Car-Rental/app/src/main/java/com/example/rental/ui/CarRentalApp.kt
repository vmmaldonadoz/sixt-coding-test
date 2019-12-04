package com.example.rental.ui

import androidx.multidex.MultiDexApplication
import com.example.rental.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class CarRentalApp : MultiDexApplication(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent
            .factory()
            .create(this)
    }

    override fun androidInjector(): AndroidInjector<Any>? {
        return dispatchingAndroidInjector
    }

}