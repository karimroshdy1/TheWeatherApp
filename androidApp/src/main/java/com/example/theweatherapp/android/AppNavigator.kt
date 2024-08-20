package com.example.theweatherapp.android

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.theweatherapp.Screen
import com.example.theweatherapp.android.AmrSherif.WeatherViewModel
import com.example.theweatherapp.android.Screens.AddLocationScreen
import com.example.theweatherapp.android.Screens.MainScreen
import com.example.theweatherapp.android.Screens.SavedLocationViewModel
import com.example.theweatherapp.android.Screens.SavedLocationsScreen
import com.example.theweatherapp.android.Screens.WeatherDetailsScreen

@Composable
fun AppNavigator(navController: NavHostController, viewModel: WeatherViewModel) {
    val navigator = AndroidNavigator(navController) // Wrapping NavHostController in a Navigator

    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route,
        modifier = Modifier

          // Removed verticalScroll modifier
    ) {
        composable(Screen.MainScreen.route) {
            MainScreen(viewModel, navigator, modifier = Modifier
                .fillMaxWidth() // Use fillMaxWidth for width filling
                .fillMaxHeight())
        }
        composable(Screen.WeatherDetailsScreen.route) {
            WeatherDetailsScreen(viewModel, navigator, modifier = Modifier
                .fillMaxWidth() // Use fillMaxWidth for width filling
                .fillMaxHeight())
        }
        composable(Screen.AddLocationScreen.route) {
            AddLocationScreen(navigator, modifier = Modifier
                .fillMaxWidth() // Use fillMaxWidth for width filling
                .fillMaxHeight())
        }
        composable(Screen.SavedLocationsScreen.route) {
            SavedLocationsScreen(SavedLocationViewModel(), navigator, modifier = Modifier
                .fillMaxWidth() // Use fillMaxWidth for width filling
                .fillMaxHeight())
        }
    }
}