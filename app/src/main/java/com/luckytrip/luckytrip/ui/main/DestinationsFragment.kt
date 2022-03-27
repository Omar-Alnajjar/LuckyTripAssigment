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
        observeChanges()
    }

    private fun setupRecyclerView() {
        adapter = DestinationsAdapter()
        adapter.itemClickListener = { _, position ->
            adapter.setSelection(position)
            destinationsViewModel.updateSelectedDestination(position)
        }
        rvDestinations.adapter = adapter
    }

    private fun observeChanges() {
        destinationsViewModel.destinationsViewState.observe(this, Observer {
            when (it) {
                is DestinationsViewState.DestinationsResponseData -> {
                    progress.visibility = View.GONE
                    rvDestinations.visibility = View.VISIBLE
                    it.destinations?.let { res ->
                        adapter.submitList(res)
                    }
                }
                is DestinationsViewState.Loading -> {
                    progress.visibility = View.VISIBLE
                    rvDestinations.visibility = View.GONE
                }
                is DestinationsViewState.Error -> {
                    progress.visibility = View.GONE
                    rvDestinations.visibility = View.VISIBLE
                    Snackbar.make(rootView, R.string.lbl_somthing_went_wrong, Snackbar.LENGTH_SHORT)
                        .show()
                }
                is DestinationsViewState.DoneClick -> {
                    //ToDo go to the next screen
                }
            }
        })
    }

    companion object {
        fun newInstance() = DestinationsFragment()
    }
}