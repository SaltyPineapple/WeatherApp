package com.pineapple.weather.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pineapple.weather.ui.theme.WeatherTheme

@Composable
fun WeatherCard() {
    Card(
        modifier = Modifier
            .size(width = 280.dp, height = 80.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f),
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    ) {
        Text(text = "This is a card", modifier = Modifier.padding(5.dp))
    }
}


@Preview
@Composable
fun WeatherCardPreview() {
    WeatherTheme {
        WeatherCard()
    }
}