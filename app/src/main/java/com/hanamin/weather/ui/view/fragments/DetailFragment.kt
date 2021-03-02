package com.hanamin.weather.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.hanamin.weather.BR
import com.hanamin.weather.R
import com.hanamin.weather.databinding.FragmentDetailBinding
import com.hanamin.weather.ui.base.BaseFragment
import com.hanamin.weather.ui.viewmodel.DetailVm
import com.hanamin.weather.utils.extensions.autoCleared
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : BaseFragment() {

    private val vm by viewModels<DetailVm>()
    private var binding by autoCleared<FragmentDetailBinding>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        DataBindingUtil.inflate<FragmentDetailBinding>(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        ).also {
            binding = it
        }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.setVariable(BR.vm, vm)
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        //color status bar
        activity?.window?.statusBarColor = resources.getColor(R.color.status)


        val name = arguments?.getString("nameCity")
        if (!name.isNullOrEmpty())
            vm.getDetailList(name, view)
    }
}