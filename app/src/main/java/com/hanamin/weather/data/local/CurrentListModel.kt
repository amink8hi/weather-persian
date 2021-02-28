package com.hanamin.weather.data.local

data class CurrentListModel(
    val weatherID: Int? = 0,
    val nameCity: String? = "",
    val nameCountry: String? = "",
    val description: String? = "",
    val temp: Double? = 0.0,
    val temp_min: Double? = 0.0,
    val temp_max: Double? = 0.0,
    val humidity: Int? = 0,
    val speed: Double? = 0.0
)