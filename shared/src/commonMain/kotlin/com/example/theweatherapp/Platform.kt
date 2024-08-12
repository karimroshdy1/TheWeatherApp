package com.example.theweatherapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform