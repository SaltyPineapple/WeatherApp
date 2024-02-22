package com.pineapple.weather.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pineapple.weather.ui.theme.WeatherTheme

interface WeatherDestination {
    val text: String
    val icon: ImageVector
    val route: String
    val screen: @Composable () -> Unit
}

object Locations: WeatherDestination {
    override val text = "Current Location"
    override val icon = Icons.Default.LocationOn
    override val route = "LocationScreen"
    override val screen: @Composable () -> Unit = { }
}

object Profile: WeatherDestination {
    override val text = "Saved Locations"
    override val icon = Icons.Default.Favorite
    override val route = "ProfileScreen"
    override val screen: @Composable () -> Unit = { }
}

val navBarItems = listOf(Locations, Profile)

@Composable
fun WeatherNavBar(navController: NavHostController, backStackEntry: State<NavBackStackEntry?>){
    NavigationBar(
        modifier = Modifier
            .padding(36.dp)
            .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp)),
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        navBarItems.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            val easeUp by animateDpAsState(
                targetValue = if (selected) (-8).dp else 5.dp,
                animationSpec = spring(stiffness = StiffnessLow, dampingRatio = DampingRatioLowBouncy),
                label = "easeUp"
            )

            val easeOut by animateFloatAsState(
                targetValue = if (selected) 1f else 0f,
                animationSpec = tween(150, easing = LinearEasing),
                label = "easeOut"
            )

            NavigationBarItem(
                selected = selected,
                onClick = { navController.navigate(item.route) },
                label = {
                    Text(
                        text = item.text,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.alpha(easeOut)
                    )
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = "${item.text} Icon", modifier = Modifier.size(32.dp))
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                modifier = Modifier
                    .offset(y = easeUp)
            )
        }
    }
}

@Preview
@Composable
fun WeatherNavBarPreview(){
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    Surface(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.onPrimary),
        color = Transparent
    ){
        WeatherTheme {
            WeatherNavBar(navController = navController, backStackEntry = backStackEntry)
        }
    }
}