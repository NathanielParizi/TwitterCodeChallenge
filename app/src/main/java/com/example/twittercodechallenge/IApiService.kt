package com.example.twittercodechallenge


import com.example.twittercodechallenge.model.PATH_FUTURE
import com.example.twittercodechallenge.model.PATH_WEATHER
import com.example.twittercodechallenge.model.WeatherApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface IApiService {

    @GET(PATH_WEATHER)
    suspend fun getWeather(
    ): Response<WeatherApiResponse>

    @GET(PATH_FUTURE)
    suspend fun getFutureWeather(
    ): Response<WeatherApiResponse>


}