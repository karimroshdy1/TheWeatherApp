package com.example.theweatherapp.android.Screens

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
import androidx.compose.ui.platform.LocalContext

data class Location(val name: String, val country: String)

@Composable
fun AddLocationScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<Location>>(emptyList()) }
    val context = LocalContext.current

    // Example: Simulate a search function
    fun performSearch(query: String) {
        // Example hardcoded data to simulate search results
        val allLocations = listOf(
            Location("United States", "US"),
            Location("Canada", "CA"),
            Location("United Kingdom", "GB"),
            Location("Australia", "AU"),
            Location("Germany", "DE"),
            Location("France", "FR"),
            Location("Italy", "IT"),
            Location("Spain", "ES"),
            Location("India", "IN"),
            Location("China", "CN"),
            Location("Japan", "JP"),
            Location("Brazil", "BR"),
            Location("South Africa", "ZA"),
            Location("Mexico", "MX"),
            Location("Russia", "RU"),
            Location("South Korea", "KR"),
            Location("Argentina", "AR"),
            Location("Netherlands", "NL"),
            Location("Saudi Arabia", "SA"),
            Location("Turkey", "TR"),
            Location("Sweden", "SE")
        )

        // Filter results based on the search query
        searchResults = allLocations.filter { it.name.contains(query, ignoreCase = true) }
    }

    // Call performSearch whenever searchQuery changes
    LaunchedEffect(searchQuery) {
        performSearch(searchQuery)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Burger Menu",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable {
                        // Handle burger menu click
                        Toast.makeText(context, "Burger Menu Clicked", Toast.LENGTH_SHORT).show()
                    }
            )

            TextField(
                value = searchQuery,
                onValueChange = { newValue ->
                    searchQuery = newValue
                    // Update search results based on new query
                    performSearch(newValue)
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
            items(searchResults) { location ->
                Text(
                    text = "${location.name}, ${location.country}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // Handle click event here
                            Toast.makeText(context, "Clicked on ${location.name}, ${location.country}", Toast.LENGTH_SHORT).show()
                        }
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
