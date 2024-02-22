package com.pineapple.weather.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pineapple.weather.R
import com.pineapple.weather.data.mappers.WeatherMapper
import com.pineapple.weather.data.models.HourlyPeriod
import com.pineapple.weather.data.models.HourlySnapshot
import com.pineapple.weather.data.models.QuickSnapshot
import com.pineapple.weather.ui.theme.WeatherTheme

@Composable
fun LocationHeader(locationName: String){
    Row(modifier = Modifier
        .size(width = LocalConfiguration.current.screenWidthDp.dp - 5.dp, height = 80.dp)
        .padding(horizontal = 24.dp, vertical = 8.dp)) {
        Text(text = locationName, fontSize = 40.sp, modifier = Modifier.fillMaxHeight().align(Alignment.CenterVertically))
        Icon(imageVector = Icons.Default.LocationOn,
            contentDescription = "location header",
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterVertically))
    }
}

@Composable
fun WeatherCore(quickSnapshot: QuickSnapshot) {
    Card(
        modifier = Modifier
            .size(width = LocalConfiguration.current.screenWidthDp.dp - 5.dp, height = 200.dp)
            .padding(horizontal = 24.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.0f),
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    ){
        Row {
            Column {
                Text(text = "${quickSnapshot.currentTemp}°", fontSize = 80.sp)
                Text(text = quickSnapshot.shortForecast, fontSize = 18.sp)
            }
            Box {
                Image(
                    painter = painterResource(id = quickSnapshot.weatherImage),
                    contentDescription = "Weather Image",
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterEnd),
                )
            }
        }
    }
}

@Composable
fun WeatherCoreHourly(hourlyPeriods: List<HourlyPeriod>, detailedForecastDaily: String) {
    val hourlySnapshots = mutableListOf<HourlySnapshot>()
    for (period in hourlyPeriods) {
        hourlySnapshots.add(WeatherMapper().mapToHourlySnapshot(period))
    }

    Card(
        modifier = Modifier
//            .size(width = LocalConfiguration.current.screenWidthDp.dp - 5.dp, height = 100.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f),
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    ) {
        Box(modifier = Modifier.background(
            Brush.linearGradient(
            listOf(
                MaterialTheme.colorScheme.primaryContainer,
                MaterialTheme.colorScheme.onPrimary
            )
        ))){

        }
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = detailedForecastDaily, modifier = Modifier.padding(8.dp))
            Divider(color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f), thickness = 1.dp)
            LazyRow(){
                items(16) { index ->
                    HourlySnapshotItem(hourlySnapshots[index])
                }
            }
        }
    }
}

@Composable
fun HourlySnapshotItem(hourlySnapshot: HourlySnapshot) {
    Column(modifier = Modifier
        .width(60.dp)
        .padding(5.dp)) {
        Text(text = "7 PM", textAlign = TextAlign.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp))
        Image(painter = painterResource(id = hourlySnapshot.weatherImage),
            contentDescription = "Hourly Weather Icon",
            alignment = Alignment.Center,
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .fillMaxWidth()
                .size(25.dp))
        Text(text = "${hourlySnapshot.temperature}°",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp))
        Row(modifier = Modifier
//            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)) {
            Icon(painter = painterResource(hourlySnapshot.precipitationIcon),
                modifier = Modifier.size(16.dp),
                contentDescription = "precipitation icon")
            Text(text = "${hourlySnapshot.precipitationProbability}%", fontSize = 12.sp)
        }
    }
}

@Preview
@Composable
fun LocationHeaderPreview(){
    WeatherTheme {
        LocationHeader(locationName = "Issaquah")
    }
}

@Preview
@Composable
fun WeatherCorePreview(){
    val quickSnapshot = QuickSnapshot(
        "Spokane",
        "WA",
        56,
        15,
        "10 to 20 mph",
        "NW",
        "Mostly Cloudy",
        R.drawable.cloudy_day
    )

    WeatherTheme {
        WeatherCore(quickSnapshot = quickSnapshot)
    }
}

@Preview
@Composable
fun HourlySnapshotItemPreview(){
    val hourlySnapshot = HourlySnapshot(
        temperature = 56,
        weatherImage = R.drawable.cloudy_night,
        precipitationProbability = 70,
        precipitationIcon = R.drawable.water_mid
    )

    WeatherTheme {
        HourlySnapshotItem(hourlySnapshot = hourlySnapshot)
    }
}