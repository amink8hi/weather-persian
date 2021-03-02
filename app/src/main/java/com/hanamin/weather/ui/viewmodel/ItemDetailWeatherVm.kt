package com.hanamin.weather.ui.viewmodel

import com.hanamin.weather.data.remote.responce.fiveDailyWeather.ListModel
import com.hanamin.weather.utils.DateUtils
import com.hanamin.weather.utils.WeatherUtils
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat

class ItemDetailWeatherVm(val data: ListModel?) {
    val temp = data?.main?.temp.toString() + "°C "
    val rawResAnim = WeatherUtils().getWeatherAnimation(data!!.weather[0].id)
    val pdate = PersianDate(DateUtils.timeStampToDate(data?.dt!!))
    val pdformater1 = PersianDateFormat("l\n" + "H : i : s")

    fun date(): String {
        return pdformater1.format(pdate)
    }


}