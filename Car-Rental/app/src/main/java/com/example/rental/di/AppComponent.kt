package com.example.rental.di

import com.example.rental.CarRentalApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityBuilder::class,
        FragmentBuilder::class,
        NetworkModule::class,
        DatabaseModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<CarRentalApp> {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: CarRentalApp): AppComponent

    }

    override fun inject(instance: CarRentalApp)
}