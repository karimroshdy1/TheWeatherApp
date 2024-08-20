
package com.example.theweatherapp.android.Screens
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import com.example.theweatherapp.Navigator
import com.example.theweatherapp.Screen
import com.example.theweatherapp.android.R

data class Location(
    val name: String,
    val temperature: Int,
    val weathericon: String
)

class SavedLocationViewModel : ViewModel() {
    private val savedLocation = mutableStateListOf(
        Location("London", 20, "☀️"),
        Location("New Cairo", 12, "🌦️")
    )
    val savedLocations: List<Location> get() = savedLocation

    fun addLocation(location: Location) {
        savedLocation.add(location)
    }

    fun removeLocation(location: Location) {
        savedLocation.remove(location)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onMenuClick: (String) -> Unit) {
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
                text = { Text("Home") },
                onClick = {
                    onMenuClick("Home")
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
fun SavedLocationsScreen(
    viewModel: SavedLocationViewModel,
    navigator: Navigator,
    modifier: Modifier
) {
    val savedLocations by remember { derivedStateOf { viewModel.savedLocations } }



    val onMenuClick: (String) -> Unit = { menuItem ->
        Log.d("MainScreen", "Menu item clicked: $menuItem")
        // No action for now
        when (menuItem) {
            "Weather Details" -> navigator?.navigateTo(Screen.WeatherDetailsScreen)
            "Add Location" -> navigator?.navigateTo(Screen.AddLocationScreen)
            "Home" -> navigator?.navigateTo(Screen.MainScreen)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD3F5F5)) // Apply background color here


    ) {
        TopBar(onMenuClick)
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(savedLocations) { location ->
                LocationItem(location, onDelete = { viewModel.removeLocation(it) })
            }
        }
        FloatingActionButton(onClick = {
            // Example of adding a new location
            // val newLocation = Location("New York", 25, "☀️")
            // viewModel.addLocation(newLocation)
            navigator?.navigateTo(Screen.AddLocationScreen)
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add location")
        }
    }
}
@Composable
fun LocationItem(location: Location, onDelete: (Location) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Text(text = location.name)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "${location.temperature}°C")
        }
        Row {
            Text(text = location.weathericon, modifier = Modifier.padding(end = 16.dp))
            IconButton(onClick = { onDelete(location) }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Location")
            }
        }
    }
}


//fun PreviewSavedLocationsScreen() {
//    // Create a sample view model for preview purposes
//    val sampleViewModel = SavedLocationViewModel()
//    sampleViewModel.addLocation(Location("Preview Location", 18, "☁️"))
//
//    SavedLocationsScreen(viewModel = sampleViewModel, navigator = navigator)
//}
