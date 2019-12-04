package com.example.rental.di

import android.content.Context
import com.example.rental.CarRentalApp
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Module(includes = [AndroidInjectionModule::class])
abstract class AppModule {

    @Binds
    @AppContext
    abstract fun provideAppContext(application: CarRentalApp): Context

}