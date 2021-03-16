package com.example.twittercodechallenge

import org.assertj.core.api.Java6Assertions.assertThat
import org.assertj.core.api.Java6Assertions.within
import org.assertj.core.data.Offset
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class TemperatureConverterTests {
    @Test
    fun testCelsiusToFahrenheitConversion() {
        val precision: Offset<Float> = within(0.01f)
        assertThat(TemperatureConverter.celsiusToFahrenheit(-50f)).isEqualTo(-58f, precision)
        assertThat(TemperatureConverter.celsiusToFahrenheit(0f)).isEqualTo(32f, precision)
        assertThat(TemperatureConverter.celsiusToFahrenheit(10f)).isEqualTo(50f, precision)
        assertThat(TemperatureConverter.celsiusToFahrenheit(21.11f)).isEqualTo(70f, precision)
        assertThat(TemperatureConverter.celsiusToFahrenheit(37.78f)).isEqualTo(100f, precision)
        assertThat(TemperatureConverter.celsiusToFahrenheit(100f)).isEqualTo(212f, precision)
        assertThat(TemperatureConverter.celsiusToFahrenheit(1000f)).isEqualTo(1832f, precision)
    }
    @Test
    fun testStandardDeviation() {
        val precision: Offset<Double> = within(0.01)
        assertThat(TemperatureConverter.calculateStandardDeviation(arrayListOf(1.0,2.0,3.0,4.0,7.0))).isEqualTo(2.05, precision)
    }
}
