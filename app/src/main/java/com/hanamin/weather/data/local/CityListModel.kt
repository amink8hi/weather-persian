package com.hanamin.weather.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["listCity"], unique = true)])
data class CityListModel(

    val listCity: String? = "",
    val currentCity: Boolean? = false
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}



