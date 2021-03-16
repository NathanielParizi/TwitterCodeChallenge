package com.example.twittercodechallenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.twittercodechallenge.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.temperature.text = TemperatureConverter.celsiusToFahrenheit(34F).toString()
    }
}