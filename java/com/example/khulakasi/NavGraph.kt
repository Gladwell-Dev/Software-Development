package com.example.khulakasi

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState

sealed class Screen(val route: String, val iconRes: Int, val label: String) {
    object Main : Screen("main", R.drawable.home_40px, "Home")
    object Chart : Screen("chart", R.drawable.show_chart_40px, "Show Chart")
    object Search : Screen("search", R.drawable.category_search_48px, "Search")
    object Dashboard : Screen("dashboard", R.drawable.dashboard_40px, "DashBoard")
    object Settings : Screen("settings", R.drawable.settings_20px, "Settings")

    object Notifications : Screen("notifications", R.drawable.circle_notifications_24px, "Notifications")
    object Profile : Screen("Profile", R.drawable.person_24px, "Profile")

    object PassengerManager : Screen("passenger_manager", R.drawable.person_24px, "Passenger Manager")
    object Income : Screen("income", R.drawable.show_chart_40px, "Income")
    object Expenses : Screen("expenses", R.drawable.dashboard_40px, "Expenses")
    object AddVehicles : Screen("add_vehicles", R.drawable.dashboard_40px, "Add Vehicles")
    object Reports : Screen("Reports", R.drawable.dashboard_40px, "Reports")
    object AddExpenses : Screen("add_expenses", R.drawable.dashboard_40px, "Add Expenses")

    object AdminManagement : Screen("admin_management", R.drawable.dashboard_40px, "Admin Management")
    object VerifyMe : Screen("verify_me", R.drawable.dashboard_40px, "Verify Me")
    object BookingTrip : Screen("booking_trip", R.drawable.dashboard_40px, "Booking Trip")
}

private val BottomDestinations = listOf(
    Screen.Main,
    Screen.Chart,
    Screen.Search,
    Screen.Dashboard,
    Screen.Settings
)

private val SelectedGreen = Color(0xFF0E3E3E)

@Composable
fun SetupNavGraph(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val bottomRoutes = BottomDestinations.map { it.route }.toSet()
    val showBottomBar = currentRoute != null && currentRoute in bottomRoutes

    Scaffold(
        bottomBar = { if (showBottomBar) BottomBar(navController) },
        containerColor = Color.Transparent
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "Login",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("Login") {
                LoginScreen(navController = navController, onGoogleSignInClick = {})
            }

            composable("SignUp") {
                SignUpScreen(navController = navController, onGoogleSignInClick = {})
            }

            composable("splash") {
                SplashScreen(onContinue = {
                    navController.navigate("home") {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
            }

            composable(Screen.Main.route) { MainScreen(navController) }
            composable("home") { MainScreen(navController) }

            composable(Screen.Chart.route) { ChartScreen() }
            composable(Screen.Search.route) { SearchScreen() }
            composable(Screen.Dashboard.route) { DashboardScreen(navController) }
            composable(Screen.Settings.route) { AccountScreen(navController) }

            composable(Screen.Notifications.route) {
                NotificationsScreen(navController = navController)
            }

            composable(Screen.Profile.route) {
                ProfileScreen(navController)
            }

            composable(Screen.PassengerManager.route) { ManagerScreen(navController) }
            composable(Screen.Income.route) { IncomeScreen(navController, vehicles = listOf(), drivers = listOf(), onSave = { _, _, _, _ -> }) }
            composable(Screen.Expenses.route) { ExpensesScreen(navController, drivers = listOf(), onAddDriver = {}) }

            composable(Screen.AddVehicles.route) { AddVehiclesScreen(
                navController,
                vehicles = listOf(),
                onAddVehicle = { newVehicle -> } ) }

            composable(Screen.Reports.route) { ReportsScreen(navController) }
            composable(Screen.AddExpenses.route) { AddExpensesScreen(
                navController = navController,
                vehicles = listOf("Vehicle 1", "Vehicle 2", "Vehicle 3"),
                expenses = listOf("Fuel", "Maintenance", "Insurance", "Repairs", "Other"),
                onSave = { vehicle, expense, amount, notes ->
                    // Handle saving expenses data
                }
            ) }

            composable(Screen.AdminManagement.route) { AdminManagementScreen(navController) }
            composable(Screen.VerifyMe.route) { VerifyMeScreen(navController) }
            composable(Screen.BookingTrip.route) { BookingTripScreen(navController) }

            composable("delete") { DeleteScreen(navController) }
        }
    }
}

@Composable
private fun BottomBar(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val iconColor = Color(0xFF052224)
    val barHeight = 92.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(barHeight)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val w = size.width
            val h = size.height
            val baseY = 24.dp.toPx()
            val peakY = 8.dp.toPx()

            val path = Path().apply {
                moveTo(0f, baseY)
                quadraticTo(w * 0.25f, baseY, w * 0.5f, peakY)
                quadraticTo(w * 0.75f, baseY, w, baseY)
                lineTo(w, h)
                lineTo(0f, h)
                close()
            }

            drawPath(
                path = path,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.15f),
                        Color.White.copy(alpha = 0.07f)
                    ),
                    startY = 0f,
                    endY = h
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .padding(top = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomDestinations.forEach { dest ->
                val selected = currentRoute == dest.route

                val circleSize = 30.dp
                val iconSize = 22.dp

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable {
                            navController.navigate(dest.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val isSpecial = dest == Screen.Main || dest == Screen.Search
                    val showCircle = selected

                    val circleColor = when {
                        showCircle && isSpecial -> Color(0xFF093030)
                        showCircle -> Color.White.copy(alpha = 0.12f)
                        else -> Color.Transparent
                    }

                    val iconTint = when {
                        showCircle && isSpecial -> Color.White
                        showCircle -> SelectedGreen
                        else -> iconColor
                    }

                    Box(
                        modifier = Modifier.size(circleSize),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(circleColor, shape = CircleShape)
                        )

                        Icon(
                            painter = painterResource(id = dest.iconRes),
                            contentDescription = dest.label,
                            tint = iconTint,
                            modifier = Modifier.size(iconSize)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = dest.label,
                        fontSize = 10.sp,
                        color = iconColor,
                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }
        }
    }
}