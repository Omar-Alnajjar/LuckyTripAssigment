package com.luckytrip.luckytrip.data.api

import com.luckytrip.luckytrip.models.DestinationsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService{

    @GET("2.0/test/destinations")
    suspend fun getDestinations(@Query("search_value") searchText: String?, @Query("search_type") searchType: String?): DestinationsResponse
}