//package com.example.theweatherapp.android.Screens
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.ViewModel
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.Delete
//
//
//data class Location (
//    val name :String ,
//    val temperature : Int,
//    val weathericon : String
//
//)
//class SavedLocationViewModel : ViewModel(){
//    private val savedLocation = mutableStateListOf(
//        Location ("London",20,"☀\uFE0F"),
//        Location ("New Cairo ",12,"\uD83C\uDF26\uFE0F")
//    )
//    val savedLocations:List<Location>get()= savedLocation
//
//    fun addLocation(location: Location){
//        savedLocation.add(location)
//
//    }
//    fun removeLocation(location: Location){
//        savedLocation.remove(location)
//    }
//}
//
//@Composable
//fun SavedLocationsScreen (viewModel:SavedLocationViewModel) {
//    val savedLocations by remember { derivedStateOf { viewModel.savedLocations } }
//   // var showAddLocationDialog by remember { mutableStateOf(false) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp) // density-independent pixels
//    ) {
//        LazyColumn(modifier = Modifier.weight(1f)) {  // vertically 1 float
//            items(savedLocations) { location ->
//                LocationItem(location, onDelete = { viewModel.removeLocation(it) })
//            }
//        }
//        FloatingActionButton(onClick = {
//            // Example of using addLocation function????????????????
//            val newLocation = Location("New York", 25, "☀\uFE0F")
//            viewModel.addLocation(newLocation)
//        }) {
//           Icon(imageVector = Icons.Default.Add, "Add location")
//        }
//    }
//}
//@Composable
//fun LocationItem(location: Location, onDelete: (Location) -> Unit) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Row {             // column
//            Text(text = location.name)
//            Text(text = "${location.temperature}")
//        }
//        Text(text = location.weathericon, modifier = Modifier.padding(end = 16.dp))
//        IconButton(onClick = { onDelete(location) }) {
//            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Location") // remove mn el list
//        }
//    }
//}
//
////  display the Saved loc
//@Preview(showBackground = true)
//@Composable
//fun PreviewSavedLocationsScreen() {
//    SavedLocationsScreen(viewModel = SavedLocationViewModel())
//}
//
//
//
//
//
//
package com.example.theweatherapp.android.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

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

@Composable
fun SavedLocationsScreen(viewModel: SavedLocationViewModel) {
    val savedLocations by remember { derivedStateOf { viewModel.savedLocations } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD3F5F5)) // Apply background color here
            .padding(16.dp)
    ) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(savedLocations) { location ->
                LocationItem(location, onDelete = { viewModel.removeLocation(it) })
            }
        }
        FloatingActionButton(onClick = {
            // Example of adding a new location
            // val newLocation = Location("New York", 25, "☀️")
            // viewModel.addLocation(newLocation)
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

@Preview(showBackground = true)
@Composable
fun PreviewSavedLocationsScreen() {
    // Create a sample view model for preview purposes
    val sampleViewModel = SavedLocationViewModel()
    sampleViewModel.addLocation(Location("Preview Location", 18, "☁️"))

    SavedLocationsScreen(viewModel = sampleViewModel)
}
