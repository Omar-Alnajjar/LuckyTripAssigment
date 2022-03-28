package com.luckytrip.luckytrip.repository

import com.luckytrip.luckytrip.data.api.ApiHelper
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val apiHelper: ApiHelper) : MainRepository {
    override suspend fun getDestinations(searchText: String?, searchType: String?) =
        apiHelper.getDestinations(searchText, searchType)
}