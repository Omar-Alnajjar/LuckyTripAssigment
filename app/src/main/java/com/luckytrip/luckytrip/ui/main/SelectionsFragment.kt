package com.luckytrip.luckytrip.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.luckytrip.luckytrip.BR
import com.luckytrip.luckytrip.R

class SelectionsFragment : Fragment() {

    private lateinit var viewDataBinding: ViewDataBinding
    private val destinationsViewModel: DestinationsViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_selections, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.setVariable(BR.viewModel, destinationsViewModel)
    }


    companion object {
        fun newInstance() = SelectionsFragment()
    }
}