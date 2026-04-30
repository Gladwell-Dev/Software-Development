package com.example.khulakasi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun DashboardScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1FFF3))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // --- Top Bar ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                // Title
                Text(
                    text = "Categories",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF093030)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- Grid of 9 containers (3 per row) ---
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                //
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CategoryContainer(
                        modifier = Modifier.weight(1f),
                        categoryName = "Passenger Manager",
                        iconRes = R.drawable.admin_alt_24,
                        iconColor = Color.Black,
                        onClick = { navController.navigate(Screen.PassengerManager.route) }
                    )
                    CategoryContainer(
                        modifier = Modifier.weight(1f),
                        categoryName = "Income Managements",
                        iconRes = R.drawable.money_income_24,
                        iconColor = Color.Black,
                        onClick = { navController.navigate(Screen.Income.route) }
                    )
                    CategoryContainer(
                        modifier = Modifier.weight(1f),
                        categoryName = "Add Drivers",
                        iconRes = R.drawable.driver_man_24,
                        iconColor = Color.Black,
                        onClick = { navController.navigate(Screen.Expenses.route) }
                    )
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CategoryContainer(
                        modifier = Modifier.weight(1f),
                        categoryName = "Add Vehicles",
                        iconRes = R.drawable.taxi_24,
                        iconColor = Color.Black,
                        onClick = { navController.navigate(Screen.AddVehicles.route) }
                    )
                    CategoryContainer(
                        modifier = Modifier.weight(1f),
                        categoryName = "Announcements",
                        iconRes = R.drawable.advertising_24,
                        iconColor = Color.Black,
                        onClick = { navController.navigate(Screen.Reports.route) }
                    )
                    CategoryContainer(
                        modifier = Modifier.weight(1f),
                        categoryName = "manage Expenses ",
                        iconRes = R.drawable.expense_24,
                        iconColor = Color.Black,
                        onClick = { navController.navigate(Screen.AddExpenses.route) }
                    )
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CategoryContainer(
                        modifier = Modifier.weight(1f),
                        categoryName = "Admin Management",
                        iconRes = R.drawable.admin_alt_24,
                        iconColor = Color.Black,
                        onClick = { navController.navigate(Screen.AdminManagement.route) }
                    )
                    CategoryContainer(
                        modifier = Modifier.weight(1f),
                        categoryName = "Verify Me",
                        iconRes = R.drawable.octagon_check_24,
                        iconColor = Color.Black,
                        onClick = { navController.navigate(Screen.VerifyMe.route) }
                    )
                    CategoryContainer(
                        modifier = Modifier.weight(1f),
                        categoryName = "Booking Trip",
                        iconRes = R.drawable.book,
                        iconColor = Color(0xFF093030), // Keep original color for Booking Trip
                        onClick = { navController.navigate(Screen.BookingTrip.route) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Cross Icon Container (centered at the bottom)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .border(
                            width = 2.dp,
                            color = Color(0xFF052224),
                            shape = CircleShape
                        )
                        .clickable { /* Handle cross icon click */ }
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.plus_24),
                        contentDescription = "Add More",
                        modifier = Modifier.size(24.dp),
                        contentScale = ContentScale.Fit,
                        colorFilter = ColorFilter.tint(Color.Black) // Make cross icon black
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun CategoryContainer(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    iconRes: Int? = null,
    iconColor: Color = Color(0xFF093030), // Default color
    categoryName: String = "Item"
) {
    Box(
        modifier = modifier
            .height(120.dp), // Smaller height for 3 per row
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(20.dp),
                    ambientColor = Color(0x40000000),
                    spotColor = Color(0x40000000)
                )
                .background(
                    color = Color(0xE6FFFFFF),
                    shape = RoundedCornerShape(20.dp)
                )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .shadow(
                    elevation = 6.dp,
                    shape = RoundedCornerShape(18.dp),
                    ambientColor = Color(0x30000000),
                    spotColor = Color(0x30000000)
                )
                .background(
                    color = Color(0xFFF1FFF3),
                    shape = RoundedCornerShape(18.dp)
                )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(16.dp),
                    ambientColor = Color(0x20000000),
                    spotColor = Color(0x20000000)
                )
                .background(
                    color = Color(0xF0F5F5F5),
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(8.dp)
            ) {
                if (iconRes != null) {
                    Image(
                        painter = painterResource(id = iconRes),
                        contentDescription = categoryName,
                        modifier = Modifier.size(32.dp), // Smaller icon size
                        contentScale = ContentScale.Fit,
                        colorFilter = ColorFilter.tint(iconColor) // Apply color filter
                    )

                    // Underline separator
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(2.dp)
                            .background(Color(0xFF093030))
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                }

                Text(
                    text = categoryName,
                    fontSize = 12.sp, // Smaller font size
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF093030),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    maxLines = 2
                )
            }
        }
    }
}