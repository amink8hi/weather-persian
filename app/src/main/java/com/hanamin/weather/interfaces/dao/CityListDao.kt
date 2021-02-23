package com.hanamin.weather.interfaces.dao

import androidx.room.*
import com.hanamin.weather.data.local.CityListModel


@Dao
interface CityListDao {


    @Delete
    fun deletList(data: CityListModel?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertList(data: CityListModel?)

    @Query("SELECT * FROM CityListModel ")
    fun getList(): MutableList<CityListModel?>

    @Query("UPDATE CityListModel SET currentCity = :status ")
    fun updateList(status: Boolean?)

    @Query("UPDATE CityListModel SET  currentCity = :status WHERE listCity = :city")
    fun updateCity(city: String?, status: Boolean?)

    @Query("SELECT * FROM  CityListModel  WHERE currentCity = :status")
    fun getCity(status: Boolean?): CityListModel?
}