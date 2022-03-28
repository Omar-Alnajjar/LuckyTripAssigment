package com.luckytrip.luckytrip

import com.luckytrip.luckytrip.models.DestinationsResponse
import com.luckytrip.luckytrip.repository.MainRepository

class MainRepositoryMock: MainRepository {
    override suspend fun getDestinations(
        searchText: String?,
        searchType: String?
    ): DestinationsResponse {
        return DestinationsResponse(listOf())
    }
}