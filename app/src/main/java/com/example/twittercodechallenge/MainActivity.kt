package com.example.twittercodechallenge

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.example.twittercodechallenge.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

private var celsius = 0.0
private var fahrenheit = 0.0
private var cloudy = 0
private var windSpeed = .51
private var futureWeather = 0.0
private const val TAG = "MainActivity"

private lateinit var binding: ActivityMainBinding
private val service: WeatherRepository by lazy { WeatherRepository() }


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeApplication()
        fahrenheit = TemperatureConverter.celsiusToFahrenheit(34F).toDouble()
        binding.temperatureTxt.text =
            "Celsius: ${celsius.toInt()}    Fahrenheit${fahrenheit.toInt()}"
        binding.windSpeedTxt.text = "Wind Speed: " + windSpeed.toString()
        binding.stdWeather.text = "Weather forcast  " + futureWeather.toString()
        fetchCurrentWeather()
        isCloudy(cloudy)


        binding.weatherBtn.setOnClickListener {
            fetchFutureWeather()
        }
    }


    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)

        outState.putInt("celsius", celsius.toInt());
        outState.putInt("windspeed", (windSpeed*100).toInt());
        outState.putInt("cloudy", cloudy);

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        celsius = savedInstanceState.getInt("celsius", 0).toDouble()
        windSpeed = savedInstanceState.getInt("windspeed", 1).toDouble()
        windSpeed /= 100
        cloudy = savedInstanceState.getInt("cloudy", 2)
        binding.temperatureTxt.text =
            TemperatureConverter.celsiusToFahrenheit(celsius.toFloat()).toString()

    }

    fun initializeApplication() {
        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(appModule)
        }
    }

    fun isCloudy(cloudy: Int) {
        if (cloudy >= .5) {
            binding.cloudyIcon.visibility = VISIBLE
        } else {
            binding.cloudyIcon.visibility = INVISIBLE

        }
    }

    fun fetchCurrentWeather() {
        GlobalScope.launch(Dispatchers.IO) {
            Log.d(TAG, "NETWORK CALL")

            val response = service.getWeather()
            if (response.isSuccessful && response.body() != null) {
                Log.d(TAG, "basicCoroutineFetch: ${response.body()}")
                Log.d(TAG, "coroutineFetch: ${response.body()}")
//                celsius = response.body()?.weather?.temp!!.toDouble()
//                windSpeed = response.body()?.wind?.speed!!.toInt()
//                cloudy = response.body()?.clouds?.cloudiness!!


                 celsius = 14.77
                windSpeed = .51
               cloudy = 65

            }
        }
    }

    private fun fetchFutureWeather() {
        GlobalScope.launch(Dispatchers.Main) {
            Log.d(TAG, "NETWORK CALL")

            val response = service.getFutureWeather()
            if (response.isSuccessful && response.body() != null) {
                Log.d(TAG, "basicCoroutineFetch: ${response.body()}")
                Log.d(TAG, "coroutineFetch: ${response.body()}")
//                celsius = response.body()?.weather?.temp!!.toDouble()
//                windSpeed = response.body()?.wind?.speed!!.toInt()
//                cloudy = response.body()?.clouds?.cloudiness!!
//                futureWeather = response.body()?.weather?.temp!!.toDouble()

                var tempOfDays  = arrayListOf(1.0,2.0,3.0,4.0,7.0)
                var stdTemp = TemperatureConverter.calculateStandardDeviation(tempOfDays)
                celsius = 14.77
                windSpeed = .51
                cloudy = 65
                futureWeather = stdTemp
                binding.stdWeather.text = "Weather forcast: $futureWeather"
                isCloudy(cloudy)

            }
        }
    }

}