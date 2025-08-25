package com.example.weatherappudemy.network

import CurrentWeather
import com.example.weatherappudemy.data.ForecastWeather
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

interface WeatherApiService {
    @GET()
    suspend fun getCurrentWeather(@Url endUrl: String): CurrentWeather

    @GET()
    suspend fun getForecastWeather(@Url endUrl: String): ForecastWeather
}


