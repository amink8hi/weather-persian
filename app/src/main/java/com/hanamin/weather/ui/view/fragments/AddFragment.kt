package com.hanamin.weather.ui.view.fragments

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.TextView.OnEditorActionListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.hanamin.weather.BR
import com.hanamin.weather.R
import com.hanamin.weather.databinding.FragmentAddBinding
import com.hanamin.weather.ui.base.BaseFragment
import com.hanamin.weather.ui.view.customs.KitToast
import com.hanamin.weather.ui.viewmodel.AddVm
import com.hanamin.weather.utils.ConnectionChecker
import com.hanamin.weather.utils.LocationUtils
import com.hanamin.weather.utils.LocationUtils.LocationResult
import com.hanamin.weather.utils.extensions.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add.*
import javax.inject.Inject


@AndroidEntryPoint
class AddFragment : BaseFragment() {

    private val vm by viewModels<AddVm>()
    private var binding by autoCleared<FragmentAddBinding>()
    private val bundle = Bundle()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var locationUtils: LocationUtils

    @Inject
    lateinit var kitToast: KitToast

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        DataBindingUtil.inflate<FragmentAddBinding>(
            inflater,
            R.layout.fragment_add,
            container,
            false
        ).also {
            binding = it
        }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.setVariable(BR.vm, vm)
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        //array list city name
        adapter =
            ArrayAdapter<String>(
                requireContext(),
                android.R.layout.select_dialog_item,
                resources.getStringArray(R.array.name_city)
            )

        // init auto complete textview & fetch detail from weather list
        binding.acSearchWeather.setAdapter(adapter)
        binding.acSearchWeather.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (vm.loading.value == false) {
                    if (vm.searchInputText.value!!.length > 1 && !vm.searchInputText.value!!.isEmpty()) {

                        if (ConnectionChecker.isInternetAvailable(requireContext())) {
                            vm.getList(vm.searchInputText.value!!)
                        } else {
                            kitToast.errorToast(getString(R.string.no_internet_connection))
                        }
                    }
                    closeKeyboard(view)
                }
                true
            } else false
        })

        // select city
        binding.acSearchWeather.onItemClickListener =
            OnItemClickListener { parent, v, position, id ->
                if (vm.loading.value == false) {
                    val nameCity = parent.getItemAtPosition(position) as String
                    if (ConnectionChecker.isInternetAvailable(requireContext())) {
                        vm.getList(nameCity)
                    } else {
                        kitToast.errorToast(getString(R.string.no_internet_connection))
                    }
                }
                closeKeyboard(view)
            }


        //go to wheather fragment
        vm.currentWeatherModel.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                bundle.putParcelable("currentWeather", it)
                navController.navigate(R.id.action_addFragment_to_weatherFragment, bundle)
            }
        })

        //get weather with gps
        btn_gps.setOnClickListener {
            if (vm.loading.value == false) {
                if (ConnectionChecker.isInternetAvailable(requireContext())) {
                    locationUtils = LocationUtils(requireActivity(), object : LocationResult {
                        override fun getLocation(location: Location?) {
                            vm.getListWithLatLong(location?.latitude!!, location.longitude)
                            locationUtils.stopGpsTracker()
                        }
                    })
                } else {
                    kitToast.errorToast(getString(R.string.no_internet_connection))
                }
            }
        }

    }
}