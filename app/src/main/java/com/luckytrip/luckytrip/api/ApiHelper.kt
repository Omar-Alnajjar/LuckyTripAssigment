package com.luckytrip.luckytrip.api

import com.luckytrip.luckytrip.models.DestinationsResponse

interface ApiHelper {
    suspend fun getDestinations(): DestinationsResponse
}