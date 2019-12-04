package com.example.rental.di

import android.content.Context
import androidx.room.Room
import com.example.rental.data.room.CarDatabase
import com.example.rental.data.room.CarInformationDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCarDatabase(@AppContext context: Context): CarDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            CarDatabase::class.java, "cars-database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideCarInformationDao(database: CarDatabase): CarInformationDao {
        return database.carInformationDao()
    }
}