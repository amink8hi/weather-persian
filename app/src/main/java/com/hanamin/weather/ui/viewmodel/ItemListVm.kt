package com.hanamin.weather.ui.viewmodel

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.hanamin.weather.R
import com.hanamin.weather.constants.ApiConstants
import com.hanamin.weather.data.db.room.CityListDataBase
import com.hanamin.weather.data.local.CityListModel
import com.hanamin.weather.data.remote.responce.currentWeather.CurrentWeatherModel
import com.hanamin.weather.network.api.NetworkApi
import com.hanamin.weather.ui.view.customs.KitToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class ItemListVm(
    val data: CityListModel?,
    private val networkApi: NetworkApi,
    private val loading: MutableLiveData<Boolean>,
    private val kitToast: KitToast,
    private val cityListDataBase: CityListDataBase
) : ViewModel() {

    private val bundle = Bundle()

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
                Timber.d(t)
                handleError(t)
            }

        }
    }

    private fun handleList(response: CurrentWeatherModel, view: View) {
        loading.value = false

        GlobalScope.launch(Dispatchers.IO) {
            cityListDataBase.cityListDao()?.updateList(false)
            cityListDataBase.cityListDao()?.updateCity(data?.listCity, true)
        }

        bundle.putParcelable("currentWeather", response)
        view.findNavController().navigate(R.id.action_listFragment_to_weatherFragment, bundle)
    }

    private fun handleError(t: Throwable) {
        loading.value = false
        Timber.d(t)
        kitToast.errorToast("دوباره تلاش کنید")
    }


    fun selectCity(view: View) {
        getList(data?.listCity!!, view)
    }
}