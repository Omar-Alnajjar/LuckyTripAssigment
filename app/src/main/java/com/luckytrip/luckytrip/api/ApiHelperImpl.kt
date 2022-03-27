package com.luckytrip.luckytrip.api

import com.luckytrip.luckytrip.models.DestinationsResponse
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
):ApiHelper{
    override suspend fun getDestinations(): DestinationsResponse = apiService.getDestinations()
}