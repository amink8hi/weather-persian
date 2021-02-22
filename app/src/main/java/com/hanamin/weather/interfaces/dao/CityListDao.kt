package com.hanamin.weather.interfaces.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hanamin.weather.data.local.CityListModel


@Dao
interface CityListDao {


    @Delete
    fun deletList(data: MutableList<CityListModel?>)

    @Insert()
    fun insertList(data: MutableList<CityListModel?>)

    @Query("SELECT * FROM CityListModel ")
    fun getList(): MutableList<CityListModel?>

}