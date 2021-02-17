package com.hanamin.weather.network.api


import com.hanamin.weather.constants.ApiConstants
import com.hanamin.weather.data.remote.responce.currentWeather.CurrentWeatherModel
import com.hanamin.weather.data.remote.responce.fiveDailyWeather.FiveDailyWeatherModel
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface NetworkApi {


    @GET
    suspend fun GetFiveDailyWeather(
        @Url suffix: String = ApiConstants.FORECAST,
        @Query("q") city: String?,
        @Query("units") units: String?,
        @Query("lang") lang: String?,
        @Query("appid") apiKey: String?
    ): FiveDailyWeatherModel


    @GET
    suspend fun GetCurrentWeather(
        @Url suffix: String = ApiConstants.CURRENT,
        @Query("q") city: String?,
        @Query("units") units: String?,
        @Query("lang") lang: String?,
        @Query("appid") apiKey: String?
    ): CurrentWeatherModel

    @GET
    suspend fun GetCurrentWeatherWithlatlong(
        @Url suffix: String = ApiConstants.CURRENT,
        @Query("lat") lat: Double?,
        @Query("lon") long: Double?,
        @Query("units") units: String?,
        @Query("lang") lang: String?,
        @Query("appid") apiKey: String?
    ): CurrentWeatherModel

}