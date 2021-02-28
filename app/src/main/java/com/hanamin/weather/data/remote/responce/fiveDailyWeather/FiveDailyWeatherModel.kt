package com.hanamin.weather.data.remote.responce.fiveDailyWeather

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FiveDailyWeatherModel(
    /*  val cod: Int,
      val message: Int,
      val cnt: Int,*/
    val list: MutableList<ListModel?>,
    /*  val city: CityModel*/
) : Parcelable

/*@Parcelize
data class CityModel(
    val id: Int,
    val name: String,
    val coord: CoordModel,
    val country: String,
    val population: Int,
    val timezone: Int,
    val sunrise: Int,
    val sunset: Int
) : Parcelable*/

/*@Parcelize
data class CloudsModel(
    val all: Int
) : Parcelable*/

/*@Parcelize
data class CoordModel(
    val lat: Double,
    val lon: Double
) : Parcelable*/

@Parcelize
data class ListModel(
    val dt: Long? = 0,
    val main: MainModel,
    val weather: MutableList<WeatherModel>,
/*    val clouds: CloudsModel,
    val wind: WindModel,
    val visibility: Int,
    val pop: Double,
    val rain: RainModel,
    val sys: SysModel,
    val dt_txt: String*/
) : Parcelable

@Parcelize
data class MainModel(
    val temp: Double,
/*    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val sea_level: Int,
    val grnd_level: Int,
    val humidity: Int,
    val temp_kf: Double*/
) : Parcelable

/*@Parcelize
data class RainModel(
    @SerializedName("3h")
    val threeH: Double
) : Parcelable*/

/*@Parcelize
data class SysModel(
    val pod: String
) : Parcelable*/

@Parcelize
data class WeatherModel(
    val id: Int,
    /*  val main: String,*/
    val description: String,
/*    val icon: String*/
) : Parcelable

/*
@Parcelize
data class WindModel(
    val speed: Double,
    val deg: Int
) : Parcelable*/
