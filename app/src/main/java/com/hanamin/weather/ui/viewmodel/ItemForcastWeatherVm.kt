package com.hanamin.weather.ui.viewmodel

import com.hanamin.weather.data.local.FiveListModel
import com.hanamin.weather.utils.DateUtils
import com.hanamin.weather.utils.WeatherUtils
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat

class ItemForcastWeatherVm(val data: FiveListModel?) {
    val temp = data?.temp.toString() + "°C "
    val rawResAnim = WeatherUtils().getWeatherAnimation(data?.weatherID!!)
    val pdate = PersianDate(DateUtils.timeStampToDate(data?.dt!!))
    val pdformater1 = PersianDateFormat("l")

    fun date(): String {
        return pdformater1.format(pdate)
    }


}