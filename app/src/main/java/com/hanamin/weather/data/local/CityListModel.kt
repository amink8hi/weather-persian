package com.hanamin.weather.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityListModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    val listCity: String? = "",
    val currentCity: Boolean? = false
)


