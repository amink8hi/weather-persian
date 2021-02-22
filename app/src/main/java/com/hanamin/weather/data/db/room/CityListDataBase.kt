package com.hanamin.weather.data.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hanamin.weather.data.local.CityListModel
import com.hanamin.weather.interfaces.dao.CityListDao


@Database(entities = [CityListModel::class], version = 2)
abstract class CityListDataBase : RoomDatabase() {
    abstract fun cityListDao(): CityListDao?
}