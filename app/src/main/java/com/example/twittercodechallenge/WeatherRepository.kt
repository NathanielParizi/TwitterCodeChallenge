package com.example.twittercodechallenge

import com.example.twittercodechallenge.model.WeatherApiResponse
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Response


class WeatherRepository : KoinComponent {

    private val magicService: WeatherService by inject()

    suspend fun getWeather(
    ): Response<WeatherApiResponse> {
        return magicService.getWeather()

    }

    suspend fun getFutureWeather(
        days: String
    ): Response<WeatherApiResponse> {
        return magicService.getFutureWeather(days)
    }

}