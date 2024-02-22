package com.pineapple.weather.data

import com.pineapple.weather.data.models.DailyForecast
import com.pineapple.weather.data.models.HourlyForecast
import com.pineapple.weather.data.models.Points
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


private const val BASE_URL = "https://api.weather.gov/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

object WeatherApi {
    val retrofitService : WeatherService by lazy {
        retrofit.create(WeatherService::class.java)
    }
}

interface WeatherService{
    @GET("points/{firstPoint},{secondPoint}")
    suspend fun getPoints(@Path("firstPoint") firstPoint: Double, @Path("secondPoint") secondPoint: Double): Response<Points>

    @GET("gridpoints/{wfo}/{gridX}, {gridY}/forecast")
    suspend fun getForecast(@Path("wfo") wfo: String, @Path("gridX") gridX: Int, @Path("gridY") gridY: Int) : Response<DailyForecast>

    @GET("gridpoints/{wfo}/{gridX}, {gridY}/forecast/hourly")
    suspend fun getHourlyForecast(@Path("wfo") wfo: String, @Path("gridX") gridX: Int, @Path("gridY") gridY: Int) : Response<HourlyForecast>
}