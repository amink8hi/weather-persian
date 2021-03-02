package com.hanamin.weather.ui.viewmodel

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.hanamin.weather.R
import com.hanamin.weather.constants.ApiConstants
import com.hanamin.weather.data.remote.responce.fiveDailyWeather.ListModel
import com.hanamin.weather.network.api.NetworkApi
import com.hanamin.weather.ui.view.adapters.DetailWeatherAdapter
import com.hanamin.weather.ui.view.customs.KitToast
import com.hanamin.weather.utils.ConnectionChecker
import com.hanamin.weather.utils.extensions.default
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailVm @ViewModelInject constructor(
    private val networkApi: NetworkApi,
    private val kitToast: KitToast
) : ViewModel() {

    private var tag = javaClass.canonicalName
    var loading = MutableLiveData<Boolean>().default(false)
    var checkList = MutableLiveData<Boolean>().default(false)
    var message = MutableLiveData<String>().default("")
    var adapterDetailWeather =
        MutableLiveData<DetailWeatherAdapter>().default(DetailWeatherAdapter(mutableListOf()))


    fun getDetailList(city: String, view: View) {
        loading.value = true
        viewModelScope.launch {
            try {
                val response = networkApi.GetFiveDailyWeather(
                    city = city,
                    units = ApiConstants.UNITS,
                    lang = ApiConstants.LANG,
                    apiKey = ApiConstants.API_KEY
                )
                handleDetailList(response.list, view)
            } catch (t: Throwable) {
                handleErrorDetailList(t, view)
            }

        }
    }

    private fun handleDetailList(response: MutableList<ListModel?>, view: View) {
        adapterDetailWeather.value?.updateData(response)

        loading.value = false
        if (response.size == 0) {
            checkList.value = true
            message.value = view.resources.getString(R.string.empty_list)
        } else {
            checkList.value = false
        }

    }

    private fun handleErrorDetailList(t: Throwable, view: View) {
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

    fun onBackIconClick(view: View) {
        view.findNavController().popBackStack()
    }
}