package com.hanamin.weather.ui.view.fragments

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.hanamin.weather.BR
import com.hanamin.weather.R
import com.hanamin.weather.data.remote.responce.currentWeather.CurrentWeatherModel
import com.hanamin.weather.databinding.FragmentWeatherBinding
import com.hanamin.weather.ui.base.BaseFragment
import com.hanamin.weather.ui.viewmodel.WeatherVm
import com.hanamin.weather.utils.extensions.autoCleared
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WeatherFragment : BaseFragment() {

    private val vm by viewModels<WeatherVm>()
    private var binding by autoCleared<FragmentWeatherBinding>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        DataBindingUtil.inflate<FragmentWeatherBinding>(
            inflater,
            R.layout.fragment_weather,
            container,
            false
        ).also {
            binding = it
        }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.setVariable(BR.vm, vm)
        activity?.setTransparentStatusBar()


        //get model response
        val currentWeatherModel = arguments?.getParcelable<CurrentWeatherModel>("currentWeather")
        vm.responseModel(currentWeatherModel!!)

    }

    fun Activity.setTransparentStatusBar() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
        }
    }
}