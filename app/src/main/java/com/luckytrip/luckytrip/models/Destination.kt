package com.luckytrip.luckytrip.models

import com.google.gson.annotations.SerializedName

data class Destination(
    @SerializedName("airport_name") val airportName: String? = null,
    val city: String? = null,
    @SerializedName("country_code") val countryCode: String? = null,
    @SerializedName("country_name") val countryName: String? = null,
    val id: Int,
    val thumbnail: Image? = null,
    @SerializedName("top_fives_new_flag") val topFivesNewFlag: Int,
    @SerializedName("top_fives_updated_flag") val topFivesUpdatedFlag: Int,
    @SerializedName("destination_video") val destinationVideo: Video,
    var selected: Boolean
)

data class Image(
    @SerializedName("image_type") val imageType: String? = null,
    @SerializedName("image_url") val imageUrl: String? = null
)

data class Video(
    val url: String? = null,
    val thumbnail: Image? = null
)