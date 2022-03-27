package com.luckytrip.luckytrip.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.luckytrip.luckytrip.BR
import com.luckytrip.luckytrip.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_destinations.*

@AndroidEntryPoint
class DestinationsFragment : Fragment() {
    private lateinit var viewDataBinding: ViewDataBinding
    private val destinationsViewModel: DestinationsViewModel by activityViewModels()

    private lateinit var adapter: DestinationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeChanges()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_destinations, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.setVariable(BR.viewModel, destinationsViewModel)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = DestinationsAdapter(destinationsViewModel)
        adapter.itemClickListener = { _, position ->
            destinationsViewModel.updateSelectedDestination(position)
            adapter.notifyItemChanged(position)
        }
        rvDestinations.adapter = adapter
    }

    private fun observeChanges() {
        destinationsViewModel.destinationsViewState.observe(this, Observer {
            when (it) {
                is DestinationsViewState.DestinationsResponseData -> {
                    adapter.notifyDataSetChanged()
                }
                is DestinationsViewState.DoneClick -> {
                    findNavController().navigate(
                        DestinationsFragmentDirections.actionDestinationsFragmentToSelectionsFragment()
                    )
                }
            }
        })
    }
}