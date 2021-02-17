package com.hanamin.weather.utils

import com.hanamin.weather.R

class WeatherUtils {


    fun getWeatherAnimation(weatherCode: Int): Int {
        when {
            weatherCode / 100 == 2 -> {
                return R.raw.thunderstorm
            }
            weatherCode / 100 == 3 -> {
                return R.raw.drizzle
            }
            weatherCode / 100 == 5 -> {
                return R.raw.rain
            }
            weatherCode / 100 == 6 -> {
                return R.raw.snow
            }
            weatherCode / 100 == 7 -> {
                return R.raw.atmosphere
            }
            weatherCode == 800 -> {
                return R.raw.clear
            }
            weatherCode == 801 -> {
                return R.raw.few_cloudy
            }
            weatherCode == 802 -> {
                return R.raw.few_cloudy
            }
            weatherCode == 803 -> {
                return R.raw.overcast_cloudy
            }
            weatherCode == 804 -> {
                return R.raw.overcast_cloudy
            }
            else -> return R.raw.unknown
        }
    }
}