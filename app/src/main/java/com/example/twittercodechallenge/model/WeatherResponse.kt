package com.example.twittercodechallenge.model

data class WeatherResponse(
    val clouds: Clouds,
    val coord: Coord,
    val name: String,
    val rain: Rain,
    val weather: Weather,
    val wind: Wind
)