package com.example.theweatherapp.android


import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
<<<<<<< Updated upstream
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf

=======
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.theweatherapp.Greeting
>>>>>>> Stashed changes

val ourFont = FontFamily(
    Font(R.font.ubuntu_bold, FontWeight.Bold),
    Font(R.font.ubuntu_light, FontWeight.Light),
    Font(R.font.ubuntu_medium, FontWeight.Medium)
)
class MainActivity : ComponentActivity() {

    private lateinit var locationService: LocationService
    private var currentLocation by mutableStateOf("")

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

<<<<<<< Updated upstream
        locationService = LocationService(this)

        // Request location permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            getCurrentLocation()
        }

        // Set the content view
=======
>>>>>>> Stashed changes
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background

                ) {
                    GreetingView(currentLocation)
                }
            }
        }
    }


    private fun getCurrentLocation() {
        lifecycleScope.launch {
            val location: Location? = locationService.getCurrentLocation()
            location?.let {
                // Use the location data (latitude and longitude)
                val latitude = it.latitude
                val longitude = it.longitude
                currentLocation = "Location: $latitude, $longitude"
                Toast.makeText(this@MainActivity, currentLocation, Toast.LENGTH_SHORT).show()
                // Fetch weather data using the coordinates
            }?: run {
                currentLocation = "Location not available"
            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text ,
        fontFamily = ourFont)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}

