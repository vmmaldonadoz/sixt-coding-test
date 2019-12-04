package com.example.rental.data.room

import androidx.room.*
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface CarInformationDao {

    @Query("SELECT * FROM car")
    fun getAll(): Single<List<Car>>

    @Query("SELECT * FROM car WHERE id  = :carId")
    fun getCarById(carId: String): Maybe<Car>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg cars: Car)

    @Query("DELETE FROM car")
    fun clear()

    @Transaction
    fun updateCarInformation(list: List<Car>) {
        clear()
        insertAll(*list.toTypedArray())
    }
}