package com.luckytrip.luckytrip.data.prefs

import android.content.Context
import android.content.SharedPreferences

class AppSharedPreferences(private val context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun saveSelectedDestinationsIds(ids: Set<String>) =
        prefs.edit().putStringSet(KEY_DESTINATIONS_IDS, ids).apply()


    fun getSelectedDestinationsIds() = prefs.getStringSet(KEY_DESTINATIONS_IDS, null)


    companion object {
        const val PREFERENCES_NAME = "LuckyTripPreferences"
        const val KEY_DESTINATIONS_IDS = "DestinationsIds"
    }
}