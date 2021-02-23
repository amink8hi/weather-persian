package com.hanamin.weather.ui.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.hanamin.weather.BR
import com.hanamin.weather.R
import com.hanamin.weather.data.db.room.CityListDataBase
import com.hanamin.weather.data.remote.responce.currentWeather.CurrentWeatherModel
import com.hanamin.weather.databinding.FragmentWeatherBinding
import com.hanamin.weather.ui.base.BaseFragment
import com.hanamin.weather.ui.viewmodel.WeatherVm
import com.hanamin.weather.utils.extensions.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class WeatherFragment : BaseFragment() {

    private val vm by viewModels<WeatherVm>()
    private var binding by autoCleared<FragmentWeatherBinding>()

    @Inject
    lateinit var cityListDataBase: CityListDataBase

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

        //color status bar
        activity?.window?.statusBarColor = Color.TRANSPARENT

        //get model response
        val currentWeatherModel = arguments?.getParcelable<CurrentWeatherModel>("currentWeather")
        if (currentWeatherModel != null)
            vm.responseModel(currentWeatherModel)
        else
            GlobalScope.launch(Dispatchers.Main) {
                val list = withContext(Dispatchers.IO) {
                    cityListDataBase.cityListDao()?.getCity(true)
                }
                vm.getList(list?.listCity!!)
            }
        arguments?.clear()

    }

}