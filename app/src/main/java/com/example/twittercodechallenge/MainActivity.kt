package com.example.twittercodechallenge

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import com.example.twittercodechallenge.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.inject
import org.koin.core.context.startKoin

private var temperature = 0
private const val TAG = "MainActivity"

private lateinit var binding: ActivityMainBinding
private val service : WeatherRepository by lazy { WeatherRepository() }


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeApplication()
        binding.temperature.text = TemperatureConverter.celsiusToFahrenheit(34F).toString()
        coroutineFetch()
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

    fun initializeApplication() {
        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(appModule)
        }
    }

    fun coroutineFetch() {
        GlobalScope.launch() {
            Log.d(TAG, "NETWORK CALL")

            val response = service.getWeather()
            if (response.isSuccessful && response.body() != null) {
                Log.d(TAG, "basicCoroutineFetch: ${response.body()}")
                Log.d(TAG, "coroutineFetch: ${response.body()}")
            }


        }
    }

}