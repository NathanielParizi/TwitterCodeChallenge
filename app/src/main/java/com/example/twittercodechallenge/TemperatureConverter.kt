package com.example.twittercodechallenge

object TemperatureConverter {
    /**
     * Converts temperature in Celsius to temperature in Fahrenheit.
     *
     * @param temperatureInCelsius Temperature in Celsius to convert.
     * @return Temperature in Fahrenheit.
     */
    fun celsiusToFahrenheit(temperatureInCelsius: Float): Float {
        return temperatureInCelsius * 1.8f + 32
    }

    fun calculateStandardDeviation(arr: ArrayList<Double>): Double {
        var nDays = 5
        var sum = 0.0
        var std = 0.0
        for (num in arr) {
            sum += num
        }
        val average = sum / nDays
        for (num in arr) {
            std += Math.pow(num - average, 2.0)
        }
        return Math.sqrt(std / nDays)
    }
}
