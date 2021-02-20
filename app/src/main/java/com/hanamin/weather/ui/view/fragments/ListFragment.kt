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
import com.hanamin.weather.databinding.FragmentListBinding
import com.hanamin.weather.ui.base.BaseFragment
import com.hanamin.weather.ui.viewmodel.ListVm
import com.hanamin.weather.utils.extensions.autoCleared
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListFragment : BaseFragment() {

    private val vm by viewModels<ListVm>()
    private var binding by autoCleared<FragmentListBinding>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        DataBindingUtil.inflate<FragmentListBinding>(
            inflater,
            R.layout.fragment_list,
            container,
            false
        ).also {
            binding = it
        }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.setVariable(BR.vm, vm)
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)


    }
}