package com.luckytrip.luckytrip.ui.main

import com.luckytrip.luckytrip.models.Destination

sealed class DestinationsViewState {
    data class DestinationsResponseData(val destinations: List<Destination>?) : DestinationsViewState()
    object DoneClick : DestinationsViewState()
    object SortClick : DestinationsViewState()
}