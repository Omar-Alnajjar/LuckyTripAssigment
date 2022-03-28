package com.luckytrip.luckytrip.ui.main

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luckytrip.luckytrip.data.prefs.AppSharedPreferences
import com.luckytrip.luckytrip.models.Destination
import com.luckytrip.luckytrip.repository.MainRepository
import com.luckytrip.luckytrip.utils.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DestinationsViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val appSharedPreferences: AppSharedPreferences
) : ViewModel() {

    val destinationsViewState = SingleLiveData<DestinationsViewState>()

    var doneButtonEnabledObservable: ObservableField<Boolean> = ObservableField(false)
    var loadingObservable: ObservableField<Boolean> = ObservableField(false)
    var errorObservable: ObservableField<Boolean> = ObservableField(false)
    var emptyObservable: ObservableField<Boolean> = ObservableField(false)
    var sortObservable: ObservableField<Boolean> = ObservableField(false)

    private val selectedDestinations = mutableListOf<Destination>()

    private val backupDestinations: MutableList<Destination> = mutableListOf()
    private val destinations: MutableList<Destination> = mutableListOf()

    private var searchJob: Job? = null

    private var lastSearchText: String = ""

    init {
        getDestinations()
    }

    private fun getDestinations(searchText: String? = null, searchType: String? = null) {
        resetPreviousActions()
        searchJob?.cancel()
        val exceptionHandler = CoroutineExceptionHandler { _, _ ->
            errorObservable.set(true)
            loadingObservable.set(false)
        }
        searchJob = viewModelScope.launch(exceptionHandler) {
            delay(SEARCH_START_DELAY_TIME)

            errorObservable.set(false)
            loadingObservable.set(true)

            val newDestinations =
                mainRepository.getDestinations(searchText, searchType).destinations

            updateDestinations(newDestinations)

            checkLocalSelectedDestinations()
            destinationsViewState.value =
                DestinationsViewState.DestinationsResponseData(destinations)
            loadingObservable.set(false)
        }
    }

    private fun checkLocalSelectedDestinations() {
        val ids = appSharedPreferences.getSelectedDestinationsIds()
        ids?.let {
            destinations.forEach {
                if(ids.contains(it.id.toString())){
                    it.selected = true
                    selectedDestinations.add((it))
                }
            }
        }
        validateSelectedDestinations()
    }

    private fun resetPreviousActions() {
        selectedDestinations.clear()
        doneButtonEnabledObservable.set(false)
        sortObservable.set(false)
    }

    private fun updateDestinations(newDestinations: List<Destination>?) {
        destinations.clear()
        backupDestinations.clear()

        if (newDestinations.isNullOrEmpty()) {
            emptyObservable.set(true)
        } else {
            emptyObservable.set(false)
        }

        newDestinations?.let {
            destinations.addAll(it)
            backupDestinations.addAll(it)
        }
    }

    fun tryAgainDestinations() {
        getDestinations()
    }

    fun performDoneClick() {
        saveSelectedDestinations()
        destinationsViewState.value = DestinationsViewState.DoneClick
    }

    private fun saveSelectedDestinations() {
        val ids = selectedDestinations.map { it.id.toString() }.toSet()
        appSharedPreferences.saveSelectedDestinationsIds(ids)
    }

    fun performSortClick() {
        if (sortObservable.get() == false) {
            sortDestinations()
        } else {
            removeSortDestinations()
        }
        destinationsViewState.value = DestinationsViewState.SortClick
    }


    private fun sortDestinations() {
        sortObservable.set(true)
        destinations.sortBy { it.countryName }
    }

    private fun removeSortDestinations() {
        sortObservable.set(false)
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

    fun handleSearch(searchText: String) {
        if (searchText == lastSearchText) {
            return
        }
        lastSearchText = searchText

        if (searchText.length >= START_SEARCH_INDEX) {
            getDestinations(searchText, VALUE_SEARCH_TYPE)
        } else {
            searchJob?.cancel()
            getDestinations()
        }
    }

    companion object {
        const val MIN_VALID_SELECTED_DESTINATIONS = 3
        const val START_SEARCH_INDEX = 1
        const val VALUE_SEARCH_TYPE = "city_or_country"
        const val SEARCH_START_DELAY_TIME = 700L
    }
}