package com.example.theweatherapp.android.AmrSherif



import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theweatherapp.android.AmrSherif.WeatherApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val apiService: WeatherApiService) : ViewModel() {
    private val _weather = MutableStateFlow(WeatherState())
    val weather: StateFlow<WeatherState> get() = _weather

    fun fetchWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                Log.d("WeatherViewModel", "Fetching weather for city: $city")
                val response = apiService.getWeather(city, apiKey)
                Log.d("WeatherViewModel", "API Response: $response")
                _weather.value = WeatherState(
                    city = response.name,
                    temperature = response.main.temp,
                    description = response.weather[0].description,
                    windSpeed = response.wind.speed,
                    humidity = response.main.humidity
                )
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "Error fetching weather", e)
                _weather.value = WeatherState()
            }
        }
    }
}
