package com.hanamin.weather.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {


    fun timeStampToDate(time: Long): Date {
        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm a")
        val localTime = sdf.format(Date(time * 1000L))
        var date = Date()
        try {
            date = sdf.parse(localTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }
}