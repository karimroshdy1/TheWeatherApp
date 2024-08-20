package com.example.theweatherapp.android.Screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.theweatherapp.Navigator
import com.example.theweatherapp.Screen
import com.example.theweatherapp.android.R
import kotlinx.coroutines.launch



// Example: Simulate a search function
//    fun performSearch(query: String) {
//        // Example hardcoded data to simulate search results
//        val allLocations = listOf(
//            Locationn("United States", "US"),
//            Locationn("Canada", "CA"),
//            Locationn("United Kingdom", "GB"),
//            Locationn("Australia", "AU"),
//            Locationn("Germany", "DE"),
//            Locationn("France", "FR"),
//            Locationn("Italy", "IT"),
//            Locationn("Spain", "ES"),
//            Locationn("India", "IN"),
//            Locationn("China", "CN"),
//            Locationn("Japan", "JP"),
//            Locationn("Brazil", "BR"),
//            Locationn("South Africa", "ZA"),
//            Locationn("Mexico", "MX"),
//            Locationn("Russia", "RU"),
//            Locationn("South Korea", "KR"),
//            Locationn("Argentina", "AR"),
//            Locationn("Netherlands", "NL"),
//            Locationn("Saudi Arabia", "SA"),
//            Locationn("Turkey", "TR"),
//            Locationn("Sweden", "SE")
//        )
//
//        // Filter results based on the search query
//        searchResults = allLocations.filter { it.name.contains(query, ignoreCase = true) }
//    }

data class Locationn(val name: String, val country: String)

data class CityResponse(
    val geonames: List<City>
)

data class City(
    val name: String,
    val countryCode: String
)




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBartsm(onMenuClick: (String) -> Unit) {
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
                text = { Text("Home") },
                onClick = {
                    onMenuClick("Home")
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
fun AddLocationScreen(navigator: Navigator, modifier: Modifier) {
    var searchQuery by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<City>>(emptyList()) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope() // Define the coroutine scope



    val onMenuClick: (String) -> Unit = { menuItem ->
        Log.d("MainScreen", "Menu item clicked: $menuItem")
        // No action for now
        when (menuItem) {
            "Saved Locations" -> navigator?.navigateTo(Screen.SavedLocationsScreen)
            "Weather Details" -> navigator?.navigateTo(Screen.WeatherDetailsScreen)
            "Home" -> navigator?.navigateTo(Screen.MainScreen)
        }
    }

    // Create an instance of the CityApiService
    val cityApiService = CityApiService.create()

    fun performSearch(query: String) {
        // Make API call in a coroutine
        coroutineScope.launch {
            try {
                val response = cityApiService.searchCities(query, username = "whitehorse201020")
                if (response.geonames.isEmpty()) {
                    Toast.makeText(context, "No results found", Toast.LENGTH_SHORT).show()
                }
                searchResults = response.geonames
            } catch (e: Exception) {
                // Handle error
                Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Call performSearch whenever searchQuery changes
    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotBlank()) {
            performSearch(searchQuery)
        }
    }

    Column(modifier = modifier.fillMaxSize().background(Color(0xFFD3F5F5))) {

        TopBartsm(onMenuClick)



        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
//            Icon(
//                imageVector = Icons.Default.Menu,
//                contentDescription = "Burger Menu",
//                modifier = Modifier
//                    .padding(end = 8.dp)
//                    .clickable {
//                        // Handle burger menu click
//                        Toast.makeText(context, "Burger Menu Clicked", Toast.LENGTH_SHORT).show()
//                    }
//            )

            TextField(
                value = searchQuery,
                onValueChange = { newValue ->
                    searchQuery = newValue
                    // Update search results based on new query
                    if (newValue.isNotBlank()) {
                        performSearch(newValue)
                    }
                },
                placeholder = { Text("Enter city name...") },
                modifier = Modifier.weight(1f), // Takes up the remaining space
                trailingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Adjust the width and height of the LazyColumn
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(searchResults) { city ->
                Text(
                    text = "${city.name}, ${city.countryCode}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // Handle click event here
                            Toast
                                .makeText(
                                    context,
                                    "Clicked on ${city.name}, ${city.countryCode}",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
