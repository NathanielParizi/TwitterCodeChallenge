package com.example.twittercodechallenge

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.twittercodechallenge.databinding.ActivityMainBinding
import com.example.twittercodechallenge.model.WeatherApiResponse
import kotlinx.coroutines.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import retrofit2.Response

private var celsius = 0.0
private var fahrenheit = 0.0
private var cloudy = 0
private var windSpeed = 0.0
private var futureWeather = 0.0
private const val TAG = "MainActivity"
private var tempOfDay = arrayListOf<Double>()

private lateinit var binding: ActivityMainBinding
private val service: WeatherRepository by lazy { WeatherRepository() }

val loading = MutableLiveData<Boolean>()
val loadingError = MutableLiveData<String?>()
var job: Job? = null


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeApplication()
        fahrenheit = TemperatureConverter.celsiusToFahrenheit(34F).toDouble()
        updateUI()
        fetchCurrentWeather()


        binding.weatherBtn.setOnClickListener {
            fetchFutureWeather()
        }
    }

    fun updateUI() {
        binding.temperatureTxt.text =
            "Celsius: ${celsius.toInt()}    Fahrenheit${fahrenheit.toInt()}"
        binding.windSpeedTxt.text = "Wind Speed: " + windSpeed.toString()
        binding.stdWeather.text = "Weather forcast  " + futureWeather.toString()
        isCloudy(cloudy)

    }


    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)

        outState.putInt("celsius", celsius.toInt());
        outState.putInt("windspeed", (windSpeed * 100).toInt());
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

        Log.d(TAG, "NETWORK CALL")

        job = CoroutineScope(Dispatchers.IO).launch {
            val response: Response<WeatherApiResponse> =
                service.getWeather()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Log.d(TAG, "fetchCurrentWeather: ${response.body()}")
                    loadingError.value = null
                    loading.value = false
                    celsius = (response.body()?.weather?.temp ?: 0) as Double
                    windSpeed = (response.body()?.wind?.speed ?: 0) as Double
                    cloudy = response.body()?.clouds?.cloudiness ?: 0
                    updateUI()
                }
            }
        }

    }

    private fun fetchFutureWeather() {
        GlobalScope.async(Dispatchers.Main) {
            Log.d(TAG, "NETWORK CALL")
            var count = 0
            var listOfDays = arrayListOf("1", "2", "3", "4", "5")
            listOfDays.forEach {
                count++
                val response = service.getFutureWeather(it)
                if (response.isSuccessful && response.body()?.clouds != null) {
                    Log.d(TAG, "future: $count   ${response.body()} ")
                    tempOfDay.add(response.body()?.weather?.temp!!)
                    var stdTemp = TemperatureConverter.calculateStandardDeviation(tempOfDay)
                    futureWeather = stdTemp
                    binding.stdWeather.text = "Weather forcast: $futureWeather"
                    isCloudy(cloudy)

                }
            }
        }
    }
}



