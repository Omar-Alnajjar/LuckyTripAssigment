package com.luckytrip.luckytrip.api

import com.luckytrip.luckytrip.models.DestinationsResponse
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {
    override suspend fun getDestinations(
        searchText: String?,
        searchType: String?
    ): DestinationsResponse = apiService.getDestinations(searchText, searchType)
}