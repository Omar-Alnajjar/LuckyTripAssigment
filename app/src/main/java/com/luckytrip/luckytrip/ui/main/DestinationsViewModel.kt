package com.luckytrip.luckytrip.ui.main

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luckytrip.luckytrip.models.Destination
import com.luckytrip.luckytrip.models.DestinationsResponse
import com.luckytrip.luckytrip.repository.MainRepository
import com.luckytrip.luckytrip.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DestinationsViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    val destinationsViewState = MutableLiveData<DestinationsViewState>()

    var doneButtonEnabledObservable: ObservableField<Boolean> = ObservableField(false)

    private val selectedDestinations = mutableListOf<Destination>()

    private var destinationsResponse: DestinationsResponse? = null

    init {
        getDestinations()
    }

    private fun getDestinations() {
        val exceptionHandler = CoroutineExceptionHandler { _, _ ->
            destinationsViewState.value = DestinationsViewState.Error
        }
        viewModelScope.launch(exceptionHandler) {
            destinationsViewState.value = DestinationsViewState.Loading

            destinationsResponse = mainRepository.getDestinations()
            destinationsViewState.value =
                DestinationsViewState.DestinationsResponseData(destinationsResponse?.destinations)
        }
    }

    fun performDoneClick() {
        destinationsViewState.value = DestinationsViewState.DoneClick
    }

    fun updateSelectedDestination(position: Int) {
        destinationsResponse?.destinations?.get(position)?.let {
            if(selectedDestinations.contains(it)){
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

    companion object {
        const val MIN_VALID_SELECTED_DESTINATIONS = 3
    }
}