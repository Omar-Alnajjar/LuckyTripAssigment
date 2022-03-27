package com.luckytrip.luckytrip.ui.main

import com.luckytrip.luckytrip.models.Destination

sealed class DestinationsViewState {
    class DestinationsResponseData(val destinations: List<Destination>?) : DestinationsViewState()
    object Loading : DestinationsViewState()
    object Error : DestinationsViewState()
    object DoneClick : DestinationsViewState()
}