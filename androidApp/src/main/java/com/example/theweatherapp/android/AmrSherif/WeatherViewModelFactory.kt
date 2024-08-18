package com.example.theweatherapp.android.AmrSherif

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.theweatherapp.android.AmrSherif.WeatherApiService
import com.example.theweatherapp.android.AmrSherif.WeatherViewModel

class WeatherViewModelFactory(private val apiService: WeatherApiService) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
