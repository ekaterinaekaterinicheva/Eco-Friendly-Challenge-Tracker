package com.example.ecofriendlychallengeapp.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ecofriendlychallengeapp.ui.history.HistoryScreen
import com.example.ecofriendlychallengeapp.ui.home.HomeScreen
import com.example.ecofriendlychallengeapp.ui.progressLogScreen.LogProgressScreen
import com.example.ecofriendlychallengeapp.ui.setGoal.SetGoalScreen
import com.example.ecofriendlychallengeapp.viewmodel.ChallengeViewModel

@Composable
fun AppNavigationHost(navController: NavHostController = rememberNavController(), viewModel: ChallengeViewModel) {

    // Providing basic layout structure via Scaffold
    Scaffold (
    bottomBar = { BottomNavBar(navController) } // Represents a navigation bar at the bottom of the screen
    )  { innerPadding ->
            // NavHost defines all screens in the app and manages navigation among them
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.fillMaxSize().padding(innerPadding)
            ) {
                // Defining each screen as a composable function
                composable("home") { HomeScreen(navController, viewModel) }
                composable("set_goal") { SetGoalScreen(navController, viewModel) }
                composable("log_progress") { LogProgressScreen(navController, viewModel) }
                composable("history") { HistoryScreen(navController, viewModel) }
            }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Home", "home", Icons.Filled.Home, Icons.Outlined.Home),
        BottomNavItem("Set Goal", "set_goal", Icons.Filled.Star, Icons.Outlined.Star),
        BottomNavItem("Log Progress", "log_progress", Icons.Filled.Create, Icons.Outlined.Create),
        BottomNavItem("History", "history", Icons.Filled.DateRange, Icons.Outlined.DateRange)
    )

    NavigationBar {
        // Each NavigationBarItem represents one button in the bottom navigation bar
        val currentDestination = navController.currentDestination?.route
        items.forEach { item ->
            NavigationBarItem(
                selected = currentDestination == item.route,
                onClick = { navController.navigate(item.route) },
                icon = {
                    Icon(
                        imageVector = if (currentDestination == item.route)
                            item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) }
            )
        }
    }
}
data class BottomNavItem(
    val label: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)
