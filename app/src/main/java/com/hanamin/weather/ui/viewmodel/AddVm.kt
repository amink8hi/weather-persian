package com.hanamin.weather.ui.viewmodel

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanamin.weather.R
import com.hanamin.weather.constants.ApiConstants
import com.hanamin.weather.data.db.room.RoomDataBase
import com.hanamin.weather.data.local.CityListModel
import com.hanamin.weather.data.local.CurrentListModel
import com.hanamin.weather.data.remote.responce.currentWeather.CurrentWeatherModel
import com.hanamin.weather.network.api.NetworkApi
import com.hanamin.weather.ui.view.customs.KitToast
import com.hanamin.weather.utils.FileUtils
import com.hanamin.weather.utils.extensions.default
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class AddVm @ViewModelInject constructor(
    private val networkApi: NetworkApi,
    private val dataBase: RoomDataBase,
    private val kitToast: KitToast,
    private val fileUtils: FileUtils
) : ViewModel() {


    private var tag = javaClass.canonicalName
    var searchInputText = MutableLiveData<String>().default("")
    var nameCity = MutableLiveData<String>().default("")
    var loading = MutableLiveData<Boolean>().default(false)
    var status = MutableLiveData<Boolean>()


    fun getList(city: String, context: Context) {
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
                handleError(t, context)
            }

        }
    }

    private fun handleList(response: CurrentWeatherModel) {
        loading.value = false

        GlobalScope.launch(Dispatchers.IO) {
            dataBase.cityListDao()?.updateList(false)
            dataBase.cityListDao()?.insertList(CityListModel(nameCity.value, true))
            dataBase.cityListDao()?.updateCity(nameCity.value, true)

            val json = fileUtils.objToJson(
                CurrentListModel(
                    response.weather!![0].id, response.name,
                    response.sys?.country!!,
                    response.weather[0].description,
                    response.main?.temp!!,
                    response.main.temp_min,
                    response.main.temp_max,
                    response.main.humidity,
                    response.wind?.speed!!
                )
            )
            fileUtils.writeToFile("CurrentList.txt", json)
        }

        status.value = true
    }

    private fun handleError(t: Throwable, context: Context) {
        loading.value = false
        Timber.e("$tag --> $t")
        kitToast.errorToast(context.getString(R.string.no_found_city))
    }

    fun getListWithLatLong(lat: Double, long: Double, context: Context) {
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
                handleErrorLatLong(t, context)
            }

        }
    }

    private fun handleListLatLong(response: CurrentWeatherModel) {
        loading.value = false

        GlobalScope.launch(Dispatchers.IO) {

            dataBase.cityListDao()?.updateList(false)
            dataBase.cityListDao()?.insertList(CityListModel(response.name, true))
            dataBase.cityListDao()?.updateCity(response.name, true)

            val json = fileUtils.objToJson(
                CurrentListModel(
                    response.weather!![0].id, response.name,
                    response.sys?.country!!,
                    response.weather[0].description,
                    response.main?.temp!!,
                    response.main.temp_min,
                    response.main.temp_max,
                    response.main.humidity,
                    response.wind?.speed!!
                )
            )
            fileUtils.writeToFile("CurrentList.txt", json)
        }

        status.value = true
    }

    private fun handleErrorLatLong(t: Throwable, context: Context) {
        loading.value = false
        Timber.e("$tag --> $t")
        kitToast.errorToast(context.getString(R.string.no_found_city))
    }

}