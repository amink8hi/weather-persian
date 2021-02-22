package com.hanamin.weather.ui.viewmodel

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.hanamin.weather.network.api.NetworkApi
import com.hanamin.weather.ui.view.customs.KitToast

class ListVm @ViewModelInject constructor(
    private val networkApi: NetworkApi,
    private val kitToast: KitToast
) : ViewModel() {

    private var tag = javaClass.canonicalName


    fun onBackIconClick(view: View) {
        view.findNavController().popBackStack()
    }
}