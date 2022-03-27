package com.luckytrip.luckytrip.ui.main

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luckytrip.luckytrip.models.Destination
import com.luckytrip.luckytrip.repository.MainRepository
import com.luckytrip.luckytrip.utils.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DestinationsViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    val destinationsViewState = SingleLiveData<DestinationsViewState>()

    var doneButtonEnabledObservable: ObservableField<Boolean> = ObservableField(false)
    var loadingObservable: ObservableField<Boolean> = ObservableField(false)
    var errorObservable: ObservableField<Boolean> = ObservableField(false)

    private val selectedDestinations = mutableListOf<Destination>()

    private val backupDestinations: MutableList<Destination> = mutableListOf()
    private val destinations: MutableList<Destination> = mutableListOf()

    var isSortApplied = false

    init {
        getDestinations()
    }

    fun getDestinations() {
        val exceptionHandler = CoroutineExceptionHandler { _, _ ->
            errorObservable.set(true)
            loadingObservable.set(false)
        }
        viewModelScope.launch(exceptionHandler) {
            errorObservable.set(false)
            loadingObservable.set(true)

            val newDestinations = mainRepository.getDestinations().destinations

            updateDestinations(newDestinations)

            destinationsViewState.value =
                DestinationsViewState.DestinationsResponseData(destinations)
            loadingObservable.set(false)
        }
    }

    private fun updateDestinations(newDestinations: List<Destination>?) {
        destinations.clear()
        backupDestinations.clear()
        newDestinations?.let {
            destinations.addAll(it)
            backupDestinations.addAll(it)
        }
    }

    fun performDoneClick() {
        destinationsViewState.value = DestinationsViewState.DoneClick
    }

    fun performSortClick() {
        if(!isSortApplied) {
            sortDestinations()
        } else {
            removeSortDestinations()
        }
        destinationsViewState.value = DestinationsViewState.SortClick
    }


    private fun sortDestinations() {
        isSortApplied = true
        destinations.sortBy { it.countryName }
    }
    private fun removeSortDestinations() {
        isSortApplied = false
        destinations.clear()
        destinations.addAll(backupDestinations)
    }

    fun updateSelectedDestination(position: Int) {
        destinations[position].selected = !destinations[position].selected
        destinations[position].let {
            if (selectedDestinations.contains(it)) {
                selectedDestinations.remove(it)
            } else {
                selectedDestinations.add(it)
            }

            validateSelectedDestinations()
        }
    }

    private fun validateSelectedDestinations() {
        if (selectedDestinations.size >= MIN_VALID_SELECTED_DESTINATIONS) {
            doneButtonEnabledObservable.set(true)
        } else {
            doneButtonEnabledObservable.set(false)
        }
    }

    fun getSelectedDestinationsCount() = selectedDestinations.size.toString()

    fun getSelectedDestinationsNames(): String {
        var names = ""
        selectedDestinations.forEach {
            names += "${it.city} \n"
        }
        return names
    }

    fun getItemsCount() = destinations.size

    fun getItemAt(position: Int): Destination? {
        return destinations.getOrNull(position)
    }

    companion object {
        const val MIN_VALID_SELECTED_DESTINATIONS = 3
    }
}