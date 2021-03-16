package com.example.twittercodechallenge

import com.example.twittercodechallenge.model.WEATHER_API_BASE_URL
import com.example.twittercodechallenge.model.WeatherApiResponse
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Response
import retrofit2.Retrofit


class WeatherService : KoinComponent {

    private val retrofitBuilder: Retrofit.Builder by inject()
    private var apiService: IApiService = retrofitBuilder.baseUrl(WEATHER_API_BASE_URL)
        .build().create(IApiService::class.java)

    suspend fun getWeather(): Response<WeatherApiResponse> {
        return apiService.getWeather()
    }

    suspend fun getFutureWeather(): Response<WeatherApiResponse> {
        return apiService.getWeather()
    }
}