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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

@Composable
fun AdminManagementScreen(navController: NavHostController) {
    var selectedButton by remember { mutableStateOf("Vehicle Applications") }

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
            // Top Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                // Back Arrow -> navigate to Dashboard
                Image(
                    painter = painterResource(id = R.drawable.arrow_back_20px),
                    contentDescription = "Back",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(28.dp)
                        .clickable {
                            navController.navigate(Screen.Dashboard.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    contentScale = ContentScale.Fit
                )

                // Title
                Text(
                    text = "Admin Management",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF093030)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Four rounded containers with icons and numbers
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Row 1: First two containers
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Container 1: Pending Vehicle Applications
                    StatsContainer(
                        iconRes = R.drawable.taxi_24,
                        number = "0",
                        text = "Pending Vehicle Applications",
                        modifier = Modifier.weight(1f)
                    )

                    // Container 2: Pending Medical Records
                    StatsContainer(
                        iconRes = R.drawable.memo_circle_check_24,
                        number = "0",
                        text = "Pending Medical Records",
                        modifier = Modifier.weight(1f)
                    )
                }

                // Row 2: Second two containers
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Container 3: Total Drivers
                    StatsContainer(
                        iconRes = R.drawable.driver_man_24,
                        number = "3",
                        text = "Total Drivers",
                        modifier = Modifier.weight(1f)
                    )

                    // Container 4: Approved Vehicles
                    StatsContainer(
                        iconRes = R.drawable.octagon_check_24,
                        number = "0",
                        text = "Approved Vehicles",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Clickable buttons container
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf(
                        "Vehicle Applications",
                        "Medical Records",
                        "Drivers Eligibility",
                        "Announcements"
                    ).forEach { buttonText ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 4.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    if (selectedButton == buttonText) Color(0xFF0E3E3E) else Color.Transparent
                                )
                                .clickable { selectedButton = buttonText }
                                .padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = buttonText,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = if (selectedButton == buttonText) Color.White else Color(0xFF093030),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Driver Eligibility Management Container
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    // Heading
                    Text(
                        text = "Driver Eligibility Management",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF093030),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Table Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(Color(0xFFF8F9FA))
                            .padding(vertical = 12.dp, horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Driver Name",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF093030),
                            modifier = Modifier.weight(2f)
                        )
                        Text(
                            text = "License Number",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF093030),
                            modifier = Modifier.weight(2f)
                        )
                        Text(
                            text = "Short Distance",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF093030),
                            modifier = Modifier.weight(2f)
                        )
                        Text(
                            text = "Long Distance",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF093030),
                            modifier = Modifier.weight(2f)
                        )
                    }

                    // Driver Data Rows
                    DriverDataRow(
                        driverName = "Gladwell",
                        licenseNumber = "DL123456",
                        shortDistance = "45 km",
                        longDistance = "320 km"
                    )

                    DriverDataRow(
                        driverName = "Gladwell",
                        licenseNumber = "DL789012",
                        shortDistance = "38 km",
                        longDistance = "280 km"
                    )

                    DriverDataRow(
                        driverName = "Gladwell",
                        licenseNumber = "DL345678",
                        shortDistance = "52 km",
                        longDistance = "410 km"
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun StatsContainer(
    iconRes: Int,
    number: String,
    text: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon circle container
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF093030)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = text,
                    modifier = Modifier.size(32.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Number and text
            Column {
                Text(
                    text = number,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF093030)
                )
                Text(
                    text = text,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF093030),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun DriverDataRow(
    driverName: String,
    licenseNumber: String,
    shortDistance: String,
    longDistance: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp)
            .border(
                width = 1.dp,
                color = Color(0xFFE9ECEF),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = driverName,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF093030),
            modifier = Modifier.weight(2f)
        )
        Text(
            text = licenseNumber,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF093030),
            modifier = Modifier.weight(2f)
        )
        Text(
            text = shortDistance,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF093030),
            modifier = Modifier.weight(2f)
        )
        Text(
            text = longDistance,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF093030),
            modifier = Modifier.weight(2f)
        )
    }
}