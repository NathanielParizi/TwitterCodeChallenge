package com.example.twittercodechallenge.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherApiResponse(
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("name")
    val name: String,
    @SerializedName("rain")
    val rain: Rain,
    @SerializedName("weather")
    val weather: Weather,
    @SerializedName("wind")
    val wind: Wind
) : Parcelable