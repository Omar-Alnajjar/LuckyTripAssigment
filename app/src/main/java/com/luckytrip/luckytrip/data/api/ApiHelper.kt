package com.luckytrip.luckytrip.data.api

import com.luckytrip.luckytrip.models.DestinationsResponse

interface ApiHelper {
    suspend fun getDestinations(searchText: String?, searchType: String?): DestinationsResponse
}