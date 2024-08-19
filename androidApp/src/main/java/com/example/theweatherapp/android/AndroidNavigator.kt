package com.example.theweatherapp.android

import androidx.navigation.NavController
import com.example.theweatherapp.Navigator
import com.example.theweatherapp.Screen

class AndroidNavigator(private val navController: NavController) : Navigator {
    override fun navigateTo(screen: Screen) {
        navController.navigate(screen.route)
    }

    override fun goBack() {
        navController.popBackStack()
    }
}