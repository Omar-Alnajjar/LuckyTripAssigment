package com.luckytrip.luckytrip.api

import com.luckytrip.luckytrip.models.DestinationsResponse
import retrofit2.http.GET

interface ApiService{

    @GET("2.0/test/destinations")
    suspend fun getDestinations(): DestinationsResponse
}