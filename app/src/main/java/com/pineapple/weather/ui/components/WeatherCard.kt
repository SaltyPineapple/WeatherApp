package com.pineapple.weather.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pineapple.weather.R
import com.pineapple.weather.data.mappers.WeatherMapper
import com.pineapple.weather.data.models.DailyWeather
import com.pineapple.weather.data.models.HourlyPeriod
import com.pineapple.weather.data.models.HourlyWeather
import com.pineapple.weather.data.models.QuickSnapshot
import com.pineapple.weather.data.viewmodels.LocationUiState
import com.pineapple.weather.data.viewmodels.SnapshotUiState
import com.pineapple.weather.ui.theme.WeatherTheme

@Composable
fun WeatherCard(quickSnapshot: QuickSnapshot) {
    Card(
        modifier = Modifier
            .size(width = LocalConfiguration.current.screenWidthDp.dp - 5.dp, height = 100.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f),
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        onClick = {},
    ) {
        Row(modifier = Modifier.padding(horizontal = 24.dp)) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxHeight()) {
                Text(text = "${quickSnapshot.currentTemp}°", fontSize = 56.sp, modifier = Modifier.padding(5.dp))
            }
            Column(modifier = Modifier.fillMaxHeight()) {
                Text(text = "${quickSnapshot.locationCity}, ${quickSnapshot.locationState}", fontSize = 24.sp, modifier = Modifier.padding(5.dp))
                Text(text = quickSnapshot.shortForecast, modifier = Modifier.padding(5.dp))
            }
            Box(modifier = Modifier.fillMaxWidth()){
                Image(
                    painter = painterResource(id = quickSnapshot.weatherImage),
                    contentDescription = "Placeholder",
                    contentScale = ContentScale.Inside,
                    modifier = Modifier.width(80.dp).align(Alignment.CenterEnd),
                )
            }
        }
        // this ui state will not live in the card. This logic will be inside the drawer
    }
}


@Preview
@Composable
fun WeatherCardPreview() {
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
        // WeatherCard(quickSnapshot)
    }
}