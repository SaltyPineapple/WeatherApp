package com.pineapple.weather.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pineapple.weather.R
import com.pineapple.weather.ui.theme.WeatherTheme

@Composable
fun Footer() {
    Column {
        Spacer(modifier = Modifier.height(100.dp))
        Text(text = "\u00A9 Pineapple Productions", textAlign = TextAlign.Center,modifier = Modifier.fillMaxWidth().padding(8.dp))
        Image(painter = painterResource(id = R.drawable.pineapple),
            contentDescription = "Pineapple footer logo",
            contentScale = ContentScale.Inside,
            modifier = Modifier.fillMaxWidth().height(100.dp))
        Spacer(modifier = Modifier.height(150.dp))
    }
}

@Preview
@Composable
fun FooterPreview(){
    WeatherTheme {
        Footer()
    }
}