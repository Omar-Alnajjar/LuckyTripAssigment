package com.luckytrip.luckytrip.repository

import com.luckytrip.luckytrip.api.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper
){
    suspend fun getDestinations(searchText: String?, searchType: String?) = apiHelper.getDestinations(searchText, searchType)
}