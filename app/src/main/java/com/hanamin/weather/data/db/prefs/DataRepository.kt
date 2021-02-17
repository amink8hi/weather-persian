package com.hanamin.weather.data.db.prefs

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Singleton

data class DataRepository(private val context: Context) {

    private val preferences: SharedPreferences
    private val prefsTag = "com.hanamin.weather.prefs"


    var token: String?
        @Singleton
        get() = preferences.getString(prefsTag + "afsaweqweqwew", "")!!
        @Singleton
        set(value) = preferences.edit().putString(prefsTag + "afsaweqweqwew", value).apply()


    init {
        preferences = context.getSharedPreferences(
            prefsTag, Context.MODE_PRIVATE
        )
    }
}