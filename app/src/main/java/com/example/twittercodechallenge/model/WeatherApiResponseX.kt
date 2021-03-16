package com.example.twittercodechallenge.model

data class WeatherApiResponseX(
    val clouds: CloudsX,
    val coord: CoordX,
    val name: String,
    val rain: RainX,
    val weather: WeatherX,
    val wind: WindX
)