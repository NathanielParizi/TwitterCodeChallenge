package com.example.twittercodechallenge.model

data class WeatherApiResponse(
    val protocol: String, val code: Int, val message: String, val url: String
)

//{protocol=http/1.1, code=200, message=OK, url=https://twitter-code-challenge.s3.amazonaws.com/current.json}