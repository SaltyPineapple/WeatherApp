package com.pineapple.weather.data.mappers

import com.pineapple.weather.R
import com.pineapple.weather.data.models.PrecipitationProbability

class WeatherImageMapper {
    // TODO: Properly map weather image based on short forecast
    fun map(shortForecast: String?, isDaytime: Boolean?) : Int{
        if (isDaytime == true){
            when (shortForecast){
                "Clear", "Sunny" -> return R.drawable.clear_day
                "Mostly Sunny", "Partly Cloudy", "Mostly Clear" -> return R.drawable.cloudy_day
                "Chance Light Rain", "Light Rain Likely", "Rain Showers Likely" -> return R.drawable.rainy_day
            }
            return R.drawable.rainy_day
        }
        else {
            when (shortForecast){
                "Clear" -> return R.drawable.clear_night
                "Mostly Sunny", "Partly Cloudy", "Mostly Clear" -> return R.drawable.cloudy_night
                "Chance Light Rain", "Light Rain Likely", "Rain Showers Likely" -> return R.drawable.rainy_day
            }
            return R.drawable.cloudy_night;
        }
    }

    fun mapIcon(probability: Int) : Int{
        val low = 0..20
        val mid = 20..60
        val high = 60..100

        if (low.contains(probability)) return R.drawable.water_low
        if (mid.contains(probability)) return R.drawable.water_mid
        if (high.contains(probability)) return R.drawable.water_high
        return R.drawable.water_mid
    }
}