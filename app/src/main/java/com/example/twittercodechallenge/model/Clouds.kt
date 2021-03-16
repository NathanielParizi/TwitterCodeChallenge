package com.example.twittercodechallenge.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Clouds(
    @SerializedName("cloudiness")

    val cloudiness: Int
) : Parcelable