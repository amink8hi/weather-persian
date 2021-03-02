package com.hanamin.weather.ui.viewmodel

import android.os.Bundle
import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.hanamin.weather.R
import com.hanamin.weather.constants.ApiConstants
import com.hanamin.weather.data.local.CurrentListModel
import com.hanamin.weather.data.local.FiveListModel
import com.hanamin.weather.data.remote.responce.currentWeather.CurrentWeatherModel
import com.hanamin.weather.data.remote.responce.fiveDailyWeather.ListModel
import com.hanamin.weather.network.api.NetworkApi
import com.hanamin.weather.ui.view.adapters.ForcastWeatherAdapter
import com.hanamin.weather.ui.view.customs.KitToast
import com.hanamin.weather.utils.ConnectionChecker
import com.hanamin.weather.utils.FileUtils
import com.hanamin.weather.utils.WeatherUtils
import com.hanamin.weather.utils.extensions.default
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*


class WeatherVm @ViewModelInject constructor(
    private val networkApi: NetworkApi,
    private val kitToast: KitToast,
    private val fileUtils: FileUtils
) : ViewModel() {

    private var tag = javaClass.canonicalName
    val bundle = Bundle()
    var message = MutableLiveData<String>().default("")
    var checkList = MutableLiveData<Boolean>().default(false)
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
                handleError(t)
            }

        }
    }

    private fun handleList(response: CurrentWeatherModel) {
        loading.value = false
        GlobalScope.launch(Dispatchers.IO) {
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

        responseModel(
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
    }

    private fun handleError(t: Throwable) {
        loading.value = false
        Timber.e("$tag --> $t")

    }

    fun responseModel(currentListModel: CurrentListModel) {
        rawResAnim.value = WeatherUtils().getWeatherAnimation(currentListModel.weatherID!!)
        nameCity.value = currentListModel.nameCity
        if (currentListModel.nameCountry == "IR") {
            val pdate = PersianDate()
            val pdformater1 = PersianDateFormat("l , d , F")
            date.value = pdformater1.format(pdate)
            nameCountry.value = "ایران".plus(" / ")
        } else {
            date.value = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
            nameCountry.value = " / ".plus(currentListModel.nameCountry)
        }
        description.value = currentListModel.description
        temp.value = currentListModel.temp.toString().plus("°C ")
        averageTemp.value =
            "میانگین دما\n".plus(currentListModel.temp_min.toString() + " / " + currentListModel.temp_max.toString())
        humidity.value = "میزان رطوبت\n".plus(currentListModel.humidity.toString() + "%")
        speed.value = "سرعت باد\n".plus(currentListModel.speed.toString() + " m/s")

    }

    fun getListForcast(city: String, view: View) {
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
                handleErrorFoscast(t, view)
            }

        }
    }

    private fun handleListForcast(response: MutableList<ListModel?>) {
        //get list with five coefficient
        adapterForcastWeather.value = ForcastWeatherAdapter(mutableListOf())
        val list: MutableList<ListModel?> = mutableListOf()
        var json = ""
        val fiveList: MutableList<FiveListModel> = mutableListOf()

        for (i in 1..5) {
            if (response[i * 7 + (i - 1)] != null) {
                list.add(response[i * 7 + (i - 1)])
                fiveList.add(
                    i - 1, FiveListModel(
                        list[i - 1]?.dt!!, list[i - 1]?.main?.temp!!,
                        list[i - 1]?.weather!![0].id, list[i - 1]?.weather!![0].description
                    )
                )
            }
        }

        json = fileUtils.objToJson(fiveList)
        fileUtils.writeToFile("FiveList.txt", json)
        adapterForcastWeather.value?.updateData(fiveList)

        loading.value = false
        checkList.value = false

    }

    private fun handleErrorFoscast(t: Throwable, view: View) {
        Timber.e("$tag --> $t")
        loading.value = false
        if (!ConnectionChecker.isInternetAvailable(view.context)) {
            kitToast.errorToast(view.resources.getString(R.string.no_internet_connection))
        } else {
            kitToast.errorToast(view.resources.getString(R.string.communication_error))
        }
        message.value = view.resources.getString(R.string.communication_error)
        checkList.value = true
    }

    fun goToListFragment(view: View) {
        view.findNavController().navigate(R.id.action_weatherFragment_to_listFragment)
    }

    fun goToDetailFragment(view: View) {
        bundle.putString("nameCity", nameCity.value)
        view.findNavController().navigate(R.id.action_weatherFragment_to_detailFragment, bundle)
    }

    fun goToAddFragment(view: View) {
        view.findNavController()
            .navigate(R.id.action_weatherFragment_to_addFragment)
    }

    fun refresh(view: View) {
        getListForcast(nameCity.value!!, view)
        getList(nameCity.value!!)
    }
}

