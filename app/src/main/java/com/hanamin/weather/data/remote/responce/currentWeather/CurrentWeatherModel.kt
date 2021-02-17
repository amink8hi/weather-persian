package com.hanamin.weather.data.remote.responce.currentWeather

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class CurrentWeatherModel(
	val coord: Coord?,
	val weather: MutableList<Weather>? = mutableListOf(),
	val base: String? = "",
	val main: Main?,
	val visibility: Int = 0,
	val wind: Wind?,
	val clouds: Clouds?,
	val dt: Int = 0,
	val sys: Sys?,
	val timezone: Int = 0,
	val id: Int = 0,
	val name: String,
	val cod: Int
) : Parcelable

@Parcelize
data class Coord(
	val lon: Double? = 0.0,
	val lat: Double? = 0.0
) : Parcelable


@Parcelize
data class Clouds(
	val all: Int?
) : Parcelable

@Parcelize
data class Main(
	val temp: Double,
	val feels_like: Double,
	val temp_min: Double,
	val temp_max: Double,
	val pressure: Int,
	val humidity: Int
) : Parcelable

@Parcelize
data class Sys(
	val type: Int,
	val id: Int,
	val country: String,
	val sunrise: Int,
	val sunset: Int
) : Parcelable

@Parcelize
data class Weather(
	val id: Int,
	val main: String,
	val description: String,
	val icon: String
) : Parcelable

@Parcelize
data class Wind(
	val speed: Double,
	val deg: Int
) : Parcelable