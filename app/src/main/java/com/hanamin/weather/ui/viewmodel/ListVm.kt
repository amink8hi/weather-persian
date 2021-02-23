package com.hanamin.weather.ui.viewmodel

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.hanamin.weather.data.db.room.CityListDataBase
import com.hanamin.weather.network.api.NetworkApi
import com.hanamin.weather.ui.view.adapters.ListCityAdapter
import com.hanamin.weather.ui.view.customs.KitToast
import com.hanamin.weather.utils.extensions.default
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListVm @ViewModelInject constructor(
    private val networkApi: NetworkApi,
    private val cityListDataBase: CityListDataBase,
    private val kitToast: KitToast
) : ViewModel() {

    private var tag = javaClass.canonicalName
    var loading = MutableLiveData<Boolean>().default(false)

    var adapterListCity =
        MutableLiveData<ListCityAdapter>().default(
            ListCityAdapter(
                mutableListOf(),
                networkApi,
                loading,
                kitToast,
                cityListDataBase
            )
        )

    init {
        GlobalScope.launch(Dispatchers.Main) {
            val list = withContext(Dispatchers.IO) {
                cityListDataBase.cityListDao()?.getList()!!
            }
            adapterListCity.value?.updateData(list)
        }

    }

    fun onBackIconClick(view: View) {
        view.findNavController().popBackStack()
    }
}