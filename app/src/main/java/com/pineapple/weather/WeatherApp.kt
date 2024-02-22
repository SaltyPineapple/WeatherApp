package com.pineapple.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pineapple.weather.data.viewmodels.LocationViewModel
import com.pineapple.weather.ui.WeatherScaffold
import com.pineapple.weather.ui.components.Locations
import com.pineapple.weather.ui.components.Profile
import com.pineapple.weather.ui.components.WeatherNavBar
import com.pineapple.weather.ui.screens.LocationScreen
import com.pineapple.weather.ui.screens.ProfileScreen
import com.pineapple.weather.ui.theme.WeatherTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherApp(){
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    WeatherTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.onPrimary
                        )
                    )
                ),
            color = Color.Transparent
        ) {
            WeatherScaffold(
                bottomBar = { WeatherNavBar(navController = navController, backStackEntry = backStackEntry) },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { /* TODO */ },
                        modifier = Modifier.size(72.dp).offset(y = (84).dp),
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                        shape = CircleShape
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Locations",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
            ) {
                NavGraph(navController = navController)
            }
        }
    }
}

@Composable
private fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Locations.route,
){

    val locationViewModel: LocationViewModel = viewModel(factory = LocationViewModel.Factory)
    NavHost(navController = navController, startDestination = startDestination){
        composable(route = Locations.route){
            LocationScreen(locationViewModel.locationUiState)
        }
        composable(route = Profile.route){
            ProfileScreen(locationViewModel.locationUiState)
        }
    }
}