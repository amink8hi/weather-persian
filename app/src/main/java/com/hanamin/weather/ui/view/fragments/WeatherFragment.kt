package com.hanamin.weather.ui.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.hanamin.weather.BR
import com.hanamin.weather.R
import com.hanamin.weather.data.db.prefs.DataRepository
import com.hanamin.weather.data.local.CurrentListModel
import com.hanamin.weather.data.local.FiveListModel
import com.hanamin.weather.databinding.FragmentWeatherBinding
import com.hanamin.weather.ui.base.BaseFragment
import com.hanamin.weather.ui.view.adapters.ForcastWeatherAdapter
import com.hanamin.weather.ui.viewmodel.WeatherVm
import com.hanamin.weather.utils.FileUtils
import com.hanamin.weather.utils.extensions.autoCleared
import com.hanamin.weather.widget.ShowCaseView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_weather.*
import javax.inject.Inject


@AndroidEntryPoint
class WeatherFragment : BaseFragment() {

    private val vm by viewModels<WeatherVm>()
    private var binding by autoCleared<FragmentWeatherBinding>()

    @Inject
    lateinit var fileUtils: FileUtils

    @Inject
    lateinit var dataRepository: DataRepository

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


        //get five list
        val city = arguments?.getString("nameCity")
        if (city != null) {
            vm.getListForcast(city, view)
        } else {
            val fiveList = fileUtils.jsonToArrayObj<MutableList<FiveListModel>>("FiveList.txt")
            vm.adapterForcastWeather.value = ForcastWeatherAdapter(mutableListOf())
            vm.adapterForcastWeather.value?.updateData(fiveList)
        }
        arguments?.clear()


        //get current list
        val currentList = fileUtils.jsonToObj<CurrentListModel>("CurrentList.txt")
        vm.responseModel(currentList)


        //showCaseView
        if (dataRepository.once != "true") {
            Handler().postDelayed(Runnable {
                ShowCaseView(requireActivity()).createShowCase(
                    expand,
                    "در این قسمت به لیست شهرها ، جزئیات اب و هوا و بارگیری مجدد اطلاعات دسترسی دارید"
                ) {
                    dataRepository.once = "true"
                    ShowCaseView(requireActivity()).createShowCase(
                        fab,
                        "با کلیک کردن بر روی این قسمت به صفحه اضافه کردن شهر جدید می روید"
                    ) {}
                }
            }, 1000)
        }


    }

}