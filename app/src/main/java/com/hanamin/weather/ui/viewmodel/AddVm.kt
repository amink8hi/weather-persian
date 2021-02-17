package com.hanamin.weather.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanamin.weather.constants.ApiConstants
import com.hanamin.weather.data.remote.responce.currentWeather.CurrentWeatherModel
import com.hanamin.weather.network.api.NetworkApi
import com.hanamin.weather.ui.view.customs.KitToast
import com.hanamin.weather.utils.extensions.default
import kotlinx.coroutines.launch
import timber.log.Timber

class AddVm @ViewModelInject constructor(
    private val networkApi: NetworkApi,
    private val kitToast: KitToast
) : ViewModel() {
    private var tag = javaClass.canonicalName
    var searchInputText = MutableLiveData<String>().default("")
    var loading = MutableLiveData<Boolean>().default(false)
    var currentWeatherModel = MutableLiveData<CurrentWeatherModel>()


    fun getList(city: String) {
        loading.value = true
        viewModelScope.launch {
            try {
                val response = networkApi.GetCurrentWeather(
                    city = city,
                    units = ApiConstants.UNITS,
                    lang = ApiConstants.LANG,
                    apiKey = ApiConstants.API_KEY
                )
                handleList(response)
            } catch (t: Throwable) {
                Timber.d(t)
                handleError(t)
            }

        }
    }

    private fun handleList(response: CurrentWeatherModel) {
        loading.value = false
        currentWeatherModel.value = response
    }

    private fun handleError(t: Throwable) {
        loading.value = false
        Timber.d(t)
        kitToast.errorToast("شهر مورد نظر یافت نشد")
    }

    fun getListWithLatLong(lat: Double, long: Double) {
        loading.value = true
        viewModelScope.launch {
            try {
                val response = networkApi.GetCurrentWeatherWithlatlong(
                    lat = lat,
                    long = long,
                    units = ApiConstants.UNITS,
                    lang = ApiConstants.LANG,
                    apiKey = ApiConstants.API_KEY
                )
                handleListLatLong(response)
            } catch (t: Throwable) {
                Timber.d(t)
                handleErrorLatLong(t)
            }

        }
    }

    private fun handleListLatLong(response: CurrentWeatherModel) {
        loading.value = false
        currentWeatherModel.value = response
    }

    private fun handleErrorLatLong(t: Throwable) {
        loading.value = false
        Timber.d("$tag --> $t")
        kitToast.errorToast("شهر مورد نظر یافت نشد")
    }

}