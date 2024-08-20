package com.example.theweatherapp.android.Screens


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.theweatherapp.Navigator
import com.example.theweatherapp.Screen
import com.example.theweatherapp.android.AmrSherif.WeatherState
import com.example.theweatherapp.android.AmrSherif.WeatherViewModel
import com.example.theweatherapp.android.R.*


private val weatherIconMap = mapOf(
    "clear sky" to drawable.sunny,
    "few clouds" to drawable.sunwithclouds,
    "scattered clouds" to drawable.scatterredclouds,
    "broken clouds" to drawable.sunwithclouds,
    "overcast clouds" to drawable.overcastclouds,
    "shower rain" to drawable.rainshower,
    "rain" to drawable.raining,
    "thunderstorm" to drawable.thunder,
    "snow" to drawable.snowflake,
    "mist" to drawable.mist
)





@Composable
fun WeatherDetailsScreen(viewModel: WeatherViewModel, navigator: Navigator, modifier: Modifier) {
    val weatherState by viewModel.weather.collectAsState()
    val apiKey = "4e887f948b0d41e94a45b00e8d5111b0" // Use your actual API key

    // Create a refresh function
    val onRefresh = {
        Log.d("MainScreen", "Refresh button clicked")
        val city = weatherState.city ?: "Default City"
        viewModel.fetchWeather(city, apiKey)
    }

    val onMenuClick: (String) -> Unit = { menuItem ->
        Log.d("MainScreen", "Menu item clicked: $menuItem")
        // No action for now
        when (menuItem) {
            "Saved Locations" -> navigator?.navigateTo(Screen.SavedLocationsScreen)
            "Add Location" -> navigator?.navigateTo(Screen.AddLocationScreen)
            "Home" -> navigator?.navigateTo(Screen.MainScreen)
        }
    }

    Column(modifier = modifier) {
        TopBar2(onRefresh = onRefresh, onMenuClick = onMenuClick)
        DispCard2(weatherState = weatherState, onRefresh = onRefresh)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar2(onRefresh: () -> Unit, onMenuClick: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    // TopAppBar with transparent background and no title
    TopAppBar(
        colors =TopAppBarDefaults.topAppBarColors(Color(0xFFD3F5F5)) ,
        title = {},
        navigationIcon = {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu Icon",
                    Modifier
                        .height(150.dp)
                        .width(150.dp)
                )
            }
        },
        actions = {
            IconButton(onClick = onRefresh) {
                Icon(
                    painter = painterResource(id = drawable.refresh),
                    contentDescription = "Refresh Icon"
                )
            }
        },
       // colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent) // Transparent background
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
                text = { Text("Home") },
                onClick = {
                    onMenuClick("Home")
                    expanded = false
                }
                    )
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
        }
    }
}

@Composable
fun DispCard2(weatherState: WeatherState, onRefresh: () -> Unit) {
    val iconRes = getWeatherIconRes(weatherState.description)

    Column(modifier = Modifier.fillMaxSize()) {
        // Today's Card
        Card(
            modifier = Modifier.size(600.dp, 500.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFD3F5F5))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(36.dp))

                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = "Weather Icon",
                    modifier = Modifier.size(90.dp)
                )

                Spacer(modifier = Modifier.height(0.dp))
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

        // Next cards in a row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Tomorrow's Card
            Card(
                modifier = Modifier.size(100.dp, 375.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFD3F5F5))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(5.dp))

                    Image(
                        painter = painterResource(id = R.drawable.mist),
                        contentDescription = "Weather Icon",
                        modifier = Modifier.size(50.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "${15} °C",
                        fontFamily = ourfont,
                        fontWeight = FontWeight.Thin,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Windy",
                        fontFamily = ourfont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(26.dp))
                    Text(
                        text = "Wind Speed: 4.1 m/s",
                        fontFamily = ourfont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(26.dp))
                    Text(
                        text = "Humidity: 65 %",
                        fontFamily = ourfont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )
                }
            }

            // 3rd Day Card
            Card(
                modifier = Modifier.size(100.dp, 375.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFD3F5F5))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(5.dp))

                    Image(
                        painter = painterResource(id = R.drawable.sunwithclouds),
                        contentDescription = "Weather Icon",
                        modifier = Modifier.size(50.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "21°C",
                        fontFamily = ourfont,
                        fontWeight = FontWeight.Thin,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Sunny",
                        fontFamily = ourfont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(26.dp))
                    Text(
                        text = "Wind Speed: 1.2 m/s",
                        fontFamily = ourfont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(26.dp))
                    Text(
                        text = "Humidity: 93%",
                        fontFamily = ourfont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )
                }
            }
            Card(
                modifier = Modifier.size(100.dp, 375.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFD3F5F5))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(5.dp))

                    Image(
                        painter = painterResource(id = R.drawable.rainshower),
                        contentDescription = "Weather Icon",
                        modifier = Modifier.size(50.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "20°C",
                        fontFamily = ourfont,
                        fontWeight = FontWeight.Thin,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Sunny",
                        fontFamily = ourfont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(26.dp))
                    Text(
                        text = "Wind Speed: 1.9 m/s",
                        fontFamily = ourfont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(26.dp))
                    Text(
                        text = "Humidity: 85%",
                        fontFamily = ourfont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )
                }
            }
            Card(
                modifier = Modifier.size(100.dp, 375.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFD3F5F5))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(5.dp))

                    Image(
                        painter = painterResource(id = R.drawable.mist),
                        contentDescription = "Weather Icon",
                        modifier = Modifier.size(50.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "17°C",
                        fontFamily = ourfont,
                        fontWeight = FontWeight.Thin,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text =  "Cloudy",
                        fontFamily = ourfont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(26.dp))
                    Text(
                        text = "Wind Speed: 2.7 m/s",
                        fontFamily = ourfont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(26.dp))
                    Text(
                        text = "Humidity:65%",
                        fontFamily = ourfont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )
                }
            }
        }

    }
}









