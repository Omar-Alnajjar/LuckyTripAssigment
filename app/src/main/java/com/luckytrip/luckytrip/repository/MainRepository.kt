package com.luckytrip.luckytrip.repository

import com.luckytrip.luckytrip.models.DestinationsResponse


interface MainRepository{
    suspend fun getDestinations(searchText: String?, searchType: String?): DestinationsResponse
}