package com.example.twittercodechallenge

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.twittercodechallenge.databinding.ActivityMainBinding

private var temperature = 0

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.temperature.text = TemperatureConverter.celsiusToFahrenheit(34F).toString()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)

        outState.putInt("key", temperature);

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        temperature = savedInstanceState.getInt("key", 0)
        binding.temperature.text = TemperatureConverter.celsiusToFahrenheit(temperature.toFloat()).toString()
    }
}