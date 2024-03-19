package com.pineapple.weather.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pineapple.weather.R
import com.pineapple.weather.ui.theme.WeatherTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherTopBar(locationCity: String, scrollBehavior: TopAppBarScrollBehavior, snackbarHost: SnackbarHostState){
    val topBarShadow = if(scrollBehavior.state.contentOffset > 1) 8.dp else 0.dp
    val scope = rememberCoroutineScope()
    WeatherTheme {
        TopAppBar(
            title = {
                Row {
                    Text(
                        text = locationCity,
                        fontSize = 40.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat)),
                        fontWeight = FontWeight.ExtraBold
                    )
                    Icon(imageVector = Icons.Default.LocationOn,
                        contentDescription = "location header",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(30.dp))
                }
            },
            navigationIcon = {},
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                actionIconContentColor = MaterialTheme.colorScheme.tertiary),
            actions = {
                IconButton(onClick = {
                    scope.launch {
                        snackbarHost.showSnackbar(message = "Saved to favorites!", duration = SnackbarDuration.Short)
                    }
                }) {
                    Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = "Save Location Button")
                }
                IconButton(onClick = {
                    scope.launch {
                        snackbarHost.showSnackbar(message = "Opening favorites sidebar ", duration = SnackbarDuration.Short)
                    }
                }) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Open Saved Locations")
                }

            },
            scrollBehavior = scrollBehavior,
            modifier = Modifier
                .padding(0.dp)
                .shadow(topBarShadow)
        )
    }
}