package com.hanamin.weather.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class LocationUtils(private var activity: Activity, private val locationResult: LocationResult) :
    LocationListener {
    private val MY_PERMISSIONS_REQUEST_LOCATION = 99
    private var locationManager: LocationManager =
        (activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager?)!!

    init {
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            checkLocationPermission()
        } else {


            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                noGps()
            } else {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 5000, 10F, this
                )
            }
        }

    }

    override fun onLocationChanged(location: Location) {
        locationResult.getLocation(location)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        super.onStatusChanged(provider, status, extras)

    }

    override fun onProviderDisabled(provider: String) {
        noGps()
        super.onProviderDisabled(provider)
    }

    fun checkLocationPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )
            } else {
                ActivityCompat.requestPermissions(
                    activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )
            }
            false
        } else {
            true
        }
    }


    private fun noGps() {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("چی پی اس  شما غیر فعال است برای دسترسی به نام شهر شما احتیاج به فعال سازی جی پی اس است!")
            .setCancelable(false)
            .setPositiveButton(
                "اجازه دسترسی"
            ) { dialog, id -> activity.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
            .setNegativeButton(
                "لغو"
            ) { dialog, id -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }


    interface LocationResult {
        fun getLocation(location: Location?)
    }

    fun stopGpsTracker() {
        locationManager.removeUpdates(this)
    }

}