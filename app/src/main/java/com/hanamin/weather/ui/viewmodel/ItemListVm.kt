package com.hanamin.weather.ui.viewmodel

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.hanamin.weather.R
import com.hanamin.weather.constants.ApiConstants
import com.hanamin.weather.data.db.room.RoomDataBase
import com.hanamin.weather.data.local.CityListModel
import com.hanamin.weather.data.local.CurrentListModel
import com.hanamin.weather.data.remote.responce.currentWeather.CurrentWeatherModel
import com.hanamin.weather.network.api.NetworkApi
import com.hanamin.weather.ui.view.customs.KitToast
import com.hanamin.weather.utils.ConnectionChecker
import com.hanamin.weather.utils.FileUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class ItemListVm(
    val data: CityListModel?,
    private val networkApi: NetworkApi,
    private val loading: MutableLiveData<Boolean>,
    private val kitToast: KitToast,
    private val dataBase: RoomDataBase,
    private val fileUtils: FileUtils
) : ViewModel() {

    private var tag = javaClass.canonicalName
    private var bundle = Bundle()

    fun getList(city: String, view: View) {
        loading.value = true
        viewModelScope.launch {
            try {
                val response = networkApi.GetCurrentWeather(
                    city = city,
                    units = ApiConstants.UNITS,
                    lang = ApiConstants.LANG,
                    apiKey = ApiConstants.API_KEY
                )
                handleList(response, view)
            } catch (t: Throwable) {
                handleError(t, view)
            }
        }
    }

    private fun handleList(response: CurrentWeatherModel, view: View) {
        loading.value = false

        GlobalScope.launch(Dispatchers.IO) {
            dataBase.cityListDao()?.updateList(false)
            dataBase.cityListDao()?.updateCity(data?.listCity, true)

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

        bundle.putString("nameCity", data?.listCity)
        view.findNavController().navigate(R.id.action_listFragment_to_weatherFragment, bundle)

    }

    private fun handleError(t: Throwable, view: View) {
        if (!ConnectionChecker.isInternetAvailable(view.context)) {
            kitToast.errorToast(view.resources.getString(R.string.no_internet_connection))
        } else {
            kitToast.errorToast(view.resources.getString(R.string.communication_error))
        }
        loading.value = false
        Timber.e("$tag --> $t")

    }

    fun selectCity(view: View) {
        getList(data?.listCity!!, view)
    }
}