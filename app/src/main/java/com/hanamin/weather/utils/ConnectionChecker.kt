package com.hanamin.weather.utils

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import androidx.annotation.RequiresPermission
import timber.log.Timber

object ConnectionChecker {
    private val TYPE_WIFI = 1
    private val TYPE_MOBILE = 2

    /**
     * @param context
     * @return boolean
     */
    @RequiresPermission(allOf = [Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET])
    fun isInternetAvailable(context: Context): Boolean {
        var isConnected = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        try {
            cm?.let {
                val activeNetwork = cm.activeNetworkInfo
                isConnected = activeNetwork != null && activeNetwork.isConnected
            }
        } catch (ex: Exception) {
            Timber.e(ex)
        }
        return isConnected
    }

    /**
     * @param context
     * @return int
     */

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET])
    fun connectionType(context: Context): Int {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm != null) {
            val activeNetwork = cm.activeNetworkInfo
            if (null != activeNetwork) {

                if (activeNetwork.type == ConnectivityManager.TYPE_WIFI)
                    return TYPE_WIFI
                if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
                    return TYPE_MOBILE
            }
        }

        return 0
    }

    /**
     * @param connectionType
     * @return string
     */
    fun connectionTypeChecker(connectionType: Int): String? {
        var type: String? = null
        when (connectionType) {
            TYPE_WIFI -> type = "TYPE_WIFI"
            TYPE_MOBILE -> type = "TYPE_MOBILE"
            else -> {
            }
        }
        return type
    }
}
