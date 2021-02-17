package com.hanamin.weather

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp()
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()

    }

    fun initTimber() {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
        else
            Timber.plant(NotLoggingTree())
    }

    class NotLoggingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
            // just an empty block
        }
    }
}