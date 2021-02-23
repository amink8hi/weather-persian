package com.hanamin.weather.ui.viewmodel

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.hanamin.weather.R
import com.hanamin.weather.constants.ApiConstants
import com.hanamin.weather.data.remote.responce.currentWeather.CurrentWeatherModel
import com.hanamin.weather.data.remote.responce.fiveDailyWeather.ListModel
import com.hanamin.weather.network.api.NetworkApi
import com.hanamin.weather.ui.view.adapters.ForcastWeatherAdapter
import com.hanamin.weather.ui.view.customs.KitToast
import com.hanamin.weather.utils.WeatherUtils
import com.hanamin.weather.utils.extensions.default
import kotlinx.coroutines.launch
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*


class WeatherVm @ViewModelInject constructor(
    private val networkApi: NetworkApi,
    private val kitToast: KitToast
) : ViewModel() {

    private var tag = javaClass.canonicalName
    var loading = MutableLiveData<Boolean>().default(false)
    var nameCity = MutableLiveData<String>().default("")
    var nameCountry = MutableLiveData<String>().default("")
    var date = MutableLiveData<String>().default("")
    var rawResAnim = MutableLiveData<Int>().default(0)
    var description = MutableLiveData<String>().default("")
    var temp = MutableLiveData<String>().default("")
    var averageTemp = MutableLiveData<String>().default("")
    var humidity = MutableLiveData<String>().default("")
    var speed = MutableLiveData<String>().default("")
    var city = MutableLiveData<String>().default("")
    var adapterForcastWeather = MutableLiveData<ForcastWeatherAdapter>().default(
        ForcastWeatherAdapter(mutableListOf())
    )

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
        responseModel(response)
    }

    private fun handleError(t: Throwable) {
        loading.value = false
        Timber.d(t)
        kitToast.errorToast("شهر مورد نظر یافت نشد")
    }

    fun responseModel(currentWeatherModel: CurrentWeatherModel) {
        rawResAnim.value = WeatherUtils().getWeatherAnimation(currentWeatherModel.weather!![0].id)
        nameCity.value = currentWeatherModel.name
        if (currentWeatherModel.sys?.country == "IR") {
            val pdate = PersianDate()
            val pdformater1 = PersianDateFormat("l , d , F")
            date.value = pdformater1.format(pdate)
            nameCountry.value = "ایران".plus(" / ")
        } else {
            date.value = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
            nameCountry.value = " / ".plus(currentWeatherModel.sys?.country)
        }
        description.value = currentWeatherModel.weather[0].description
        temp.value = currentWeatherModel.main?.temp.toString().plus("°C ")
        averageTemp.value =
            "میانگین دما\n".plus(currentWeatherModel.main?.temp_min.toString() + " / " + currentWeatherModel.main?.temp_max.toString())
        humidity.value = "میزان رطوبت\n".plus(currentWeatherModel.main?.humidity.toString() + "%")
        speed.value = "سرعت باد\n".plus(currentWeatherModel.wind?.speed.toString() + " m/s")


        getListForcast(currentWeatherModel.name)

    }

    fun getListForcast(city: String) {
        loading.value = true
        viewModelScope.launch {
            try {
                val response = networkApi.GetFiveDailyWeather(
                    city = city,
                    units = ApiConstants.UNITS,
                    lang = ApiConstants.LANG,
                    apiKey = ApiConstants.API_KEY
                )
                handleListForcast(response.list)
            } catch (t: Throwable) {
                Timber.d(t)
                handleErrorFoscast(t)
            }

        }
    }

    private fun handleListForcast(response: MutableList<ListModel?>) {
        //get list with five coefficient
        adapterForcastWeather.value = ForcastWeatherAdapter(mutableListOf())
        val list: MutableList<ListModel?> = mutableListOf()

        for (i in 1..5) {
            if (response[i * 7 + (i - 1)] != null) {
                list.add(response[i * 7 + (i - 1)])
            }
        }

        adapterForcastWeather.value?.updateData(list)
        loading.value = false
    }

    private fun handleErrorFoscast(t: Throwable) {
        Timber.d("$tag --> $t")
        loading.value = false
    }

    fun goToListFragment(view: View) {
        view.findNavController().navigate(R.id.action_weatherFragment_to_listFragment)
    }

    fun goToAddFragment(view: View) {
        view.findNavController()
            .navigate(R.id.action_weatherFragment_to_addFragment)
    }

    fun refresh() {
        adapterForcastWeather.value = ForcastWeatherAdapter(mutableListOf())
        getList(city.value!!)
    }
}

