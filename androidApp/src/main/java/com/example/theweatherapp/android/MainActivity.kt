package com.example.theweatherapp.android

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.theweatherapp.android.AmrSherif.WeatherApiService
import com.example.theweatherapp.android.AmrSherif.WeatherViewModel
import com.example.theweatherapp.android.AmrSherif.WeatherViewModelFactory
import android.location.Geocoder
import android.location.Address
import android.util.Log
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.compose.rememberNavController
import com.example.theweatherapp.android.Screens.MainScreen


class MainActivity : ComponentActivity() {

    private lateinit var locationService: LocationService
    private lateinit var viewModel: WeatherViewModel
    private var currentLocation by mutableStateOf("")
    val ourfont = FontFamily(
        Font(R.font.ubuntu_bold, FontWeight.Bold),
        Font(R.font.ubuntu_light, FontWeight.Light),
        Font(R.font.ubuntu_medium, FontWeight.Medium)
    )

    // Register the permission request
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                getCurrentLocation()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationService = LocationService(this)

        // Log permission status
        Log.d(
            "MainActivity", "Fine Location Permission Granted: ${
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            }"
        )
        Log.d(
            "MainActivity", "Coarse Location Permission Granted: ${
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            }"
        )

        // Check permissions and request if not granted
        when {
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                getCurrentLocation()
            }

            else -> {
                // Request permission
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
        val apiService =
            Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApiService::class.java)

        // Create ViewModel manually
        viewModel = ViewModelProvider(
            this,
            WeatherViewModelFactory(apiService)
        ).get(WeatherViewModel::class.java)
        setContent {

            MyApplicationTheme {
                App()
            }
        }
    }

    @Composable
    private fun App() {
        val navController = rememberNavController()
        // Pass NavController to AppNavigator
        val navigator = AndroidNavigator(navController) // Wrapping NavHostController in a Navigator

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            // AppNavigator should control the navigation flow
            AppNavigator(navController, viewModel)
        }
    }

    private fun getCurrentLocation() {
        lifecycleScope.launch {
            try {
                val location: Location? = locationService.getCurrentLocation()
                location?.let {
                    val latitude = it.latitude
                    val longitude = it.longitude
                    Log.d(
                        "MainActivity",
                        "Fetched location: Latitude = $latitude, Longitude = $longitude"
                    )
                    currentLocation = "Location: $latitude, $longitude"
                    Toast.makeText(this@MainActivity, currentLocation, Toast.LENGTH_SHORT).show()

                    // Reverse geocoding to get city name
                    val geocoder = Geocoder(this@MainActivity)
                    val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
                    if (addresses != null && addresses.isNotEmpty()) {
                        val address = addresses[0]
                        val city = address.locality ?: "Unknown City"
                        Log.d("MainActivity", "Resolved city: $city")
                        val apiKey = "4e887f948b0d41e94a45b00e8d5111b0"
                        viewModel.fetchWeather(city, apiKey)
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Unable to get city name",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } ?: run {
                    currentLocation = "Location not available"
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error fetching location: ${e.message}")
                currentLocation = "Error fetching location"
            }
        }
    }
}
