package com.example.rental.di

import android.content.Context
import androidx.room.Room
import com.example.rental.data.room.CarDatabase
import com.example.rental.data.room.CarInformationDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideCarDatabase(context: Context): CarDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            CarDatabase::class.java, "cars-database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideCarInformationDao(database: CarDatabase): CarInformationDao {
        return database.carInformationDao()
    }
}