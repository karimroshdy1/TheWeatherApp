package com.example.theweatherapp

sealed class Screen
    (val route: String) {
    object MainScreen : Screen("MainScreen")
    object WeatherDetailsScreen : Screen("WeatherDetailsScreen")
    object AddLocationScreen : Screen("AddLocationScreen")
    object SavedLocationsScreen : Screen("SavedLocationsScreen")

// Add more screens as needed
}