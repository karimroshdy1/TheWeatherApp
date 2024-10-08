package com.example.theweatherapp.android.Screens


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.theweatherapp.android.R
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.theweatherapp.Navigator
import com.example.theweatherapp.Screen
import com.example.theweatherapp.android.AmrSherif.WeatherState
import com.example.theweatherapp.android.AmrSherif.WeatherViewModel
import kotlinx.coroutines.delay


private val weatherIconMap = mapOf(
    "clear sky" to R.drawable.sunny,
    "few clouds" to R.drawable.sunwithclouds,
    "scattered clouds" to R.drawable.scatterredclouds,
    "broken clouds" to R.drawable.sunwithclouds,
    "overcast clouds" to R.drawable.overcastclouds,
    "shower rain" to R.drawable.rainshower,
    "rain" to R.drawable.raining,
    "thunderstorm" to R.drawable.thunder,
    "snow" to R.drawable.snowflake,
    "mist" to R.drawable.mist
)

fun getWeatherIconRes(description: String?): Int {
    return weatherIconMap[description] ?: R.drawable.snowflake
}



val ourfont = FontFamily(
    Font(R.font.ubuntu_bold, FontWeight.Bold),
    Font(R.font.ubuntu_light, FontWeight.Light),
    Font(R.font.ubuntu_medium, FontWeight.Medium)
)
@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), // Optional: set a background color
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                imageVector = Icons.Outlined.LocationOn, // Replace with your loading icon resource
                contentDescription = "Loading",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Loading...",
                fontSize = 20.sp,
                color = Color.Black
            )
        }
    }
}
@Composable
fun MainScreen(viewModel: WeatherViewModel, navigator: Navigator?, modifier: Modifier) {
    val weatherState by viewModel.weather.collectAsState()
    val apiKey = "4e887f948b0d41e94a45b00e8d5111b0" // Use your actual API key

    var isLoading by remember { mutableStateOf(true) }

    // Use LaunchedEffect to handle the delay for the loading screen
    LaunchedEffect(Unit) {
        delay(2000) // 3 seconds delay
        isLoading = false
    }

    if (isLoading) {
        LoadingScreen()
        //AnimatedLoadingIcon()
    } else {
        // Main content
        Column(modifier = modifier) {
            TopBar(onRefresh = {
                Log.d("MainScreen", "Refresh button clicked")
                val city = weatherState.city ?: "Default City"
                viewModel.fetchWeather(city, apiKey)
            }, onMenuClick = { menuItem ->
                Log.d("MainScreen", "Menu item clicked: $menuItem")
                when (menuItem) {
                    "Saved Locations" -> navigator?.navigateTo(Screen.SavedLocationsScreen)
                    "Add Location" -> navigator?.navigateTo(Screen.AddLocationScreen)
                    "Weather Details" -> navigator?.navigateTo(Screen.WeatherDetailsScreen)
                }
            })
            DispCard(weatherState = weatherState, onRefresh = {
                Log.d("MainScreen", "Refresh button clicked")
                val city = weatherState.city ?: "Default City"
                viewModel.fetchWeather(city, apiKey)
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onRefresh: () -> Unit, onMenuClick: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    // TopAppBar with transparent background and no title
    TopAppBar(
        title = {},
        colors = TopAppBarDefaults.topAppBarColors(Color(0xFFD3F5F5)),
        navigationIcon = {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    modifier = Modifier
                        .height(150.dp)
                        .width(150.dp),
                    contentDescription = "Menu Icon"
                )
            }
        },
        actions = {
            IconButton(onClick = onRefresh) {
                Icon(
                    painter = painterResource(id = R.drawable.refresh),
                    contentDescription = "Refresh Icon"
                )
            }
        },
        //colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent) // Transparent background
    )

    // Dropdown menu positioned correctly
    if (expanded) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            DropdownMenuItem(
                text = { Text("Saved Locations") },
                onClick = {
                    onMenuClick("Saved Locations")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Add Location") },
                onClick = {
                    onMenuClick("Add Location")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Weather Details") },
                onClick = {
                    onMenuClick("Weather Details")
                    expanded = false
                }
            )
        }
    }
}

@Composable
fun DispCard(weatherState: WeatherState, onRefresh: () -> Unit) {
    val iconRes = getWeatherIconRes(weatherState.description)

    Column(modifier = Modifier.fillMaxSize()) {



        Card(
            modifier = Modifier.fillMaxSize(),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFD3F5F5))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Spacer(modifier = Modifier.height(36.dp))

                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = "Weather Icon",
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "${weatherState.city}",
                    fontFamily = ourfont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 50.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "${weatherState.temperature} °C",
                    fontFamily = ourfont,
                    fontWeight = FontWeight.Thin,
                    fontSize = 85.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "${weatherState.description}",
                    fontFamily = ourfont,
                    fontWeight = FontWeight.Medium,
                    fontSize = 35.sp
                )
                Spacer(modifier = Modifier.height(26.dp))
                Text(
                    text = "Wind Speed: ${weatherState.windSpeed} m/s",
                    fontFamily = ourfont,
                    fontWeight = FontWeight.Medium,
                    fontSize = 35.sp
                )
                Spacer(modifier = Modifier.height(26.dp))
                Text(
                    text = "Humidity: ${weatherState.humidity}%",
                    fontFamily = ourfont,
                    fontWeight = FontWeight.Medium,
                    fontSize = 35.sp
                )
            }
        }
    }
}








