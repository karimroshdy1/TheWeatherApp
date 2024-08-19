package com.example.theweatherapp

interface Navigator {
    fun navigateTo(screen: Screen)
    fun goBack()
}