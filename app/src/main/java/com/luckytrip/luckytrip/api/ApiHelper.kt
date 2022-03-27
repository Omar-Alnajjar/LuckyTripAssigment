package com.luckytrip.luckytrip.api

import com.luckytrip.luckytrip.models.DestinationsResponse

interface ApiHelper {
    suspend fun getDestinations(searchText: String?, searchType: String?): DestinationsResponse
}