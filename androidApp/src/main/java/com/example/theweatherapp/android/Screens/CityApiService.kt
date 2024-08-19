package com.example.theweatherapp.android.Screens

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CityApiService {
    @GET("searchJSON")
    suspend fun searchCities(
        @Query("q") query: String,
        @Query("maxRows") maxRows: Int = 10,
        @Query("username") username: String // GeoNames username
    ): CityResponse

    companion object {
        private const val BASE_URL = "http://api.geonames.org/"

        fun create(): CityApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CityApiService::class.java)
        }
    }
}

