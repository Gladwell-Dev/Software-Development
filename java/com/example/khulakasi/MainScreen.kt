package com.example.khulakasi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import androidx.compose.animation.core.animateDpAsState

@Composable
fun MainScreen(navController: NavHostController) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()
    var selectedFilter by remember { mutableStateOf("Daily") }
    var searchQuery by remember { mutableStateOf("") }
    var showAllContainers by remember { mutableStateOf(false) } // State for "See All" functionality

    // ---- Fonts ----
    val LagoteFont = FontFamily(Font(R.font.logote))
    val NotoSansFont = FontFamily(Font(R.font.noto_sans))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFDFF7E2)) // Light Green background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Full scrollable content
                .padding(bottom = 100.dp)
        ) {
            // ---- Top bar with profile image + greeting ----
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Bigger circle image container with white background
                    Box(
                        modifier = Modifier
                            .size(80.dp) // Increased from 60dp
                            .clip(CircleShape)
                            .background(Color.White), // White background
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.man),
                            contentDescription = "User Photo",
                            modifier = Modifier
                                .size(50.dp) // Image size within the white container
                                .clip(CircleShape)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Good Evening",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF052224),
                            fontFamily = NotoSansFont // Noto Sans font
                        )
                        Text(
                            text = "Gladwell",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF052224),
                            fontFamily = LagoteFont // Lagote font
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.circle_notifications_24px),
                        contentDescription = "Notifications",
                        modifier = Modifier
                            .size(34.dp)
                            .clickable { navController.navigate("notifications") }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Image(
                        painter = painterResource(id = R.drawable.person_24px),
                        contentDescription = "Profile",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { navController.navigate("profile") }
                    )
                }
            }

            // Push the Search Bar down more
            Spacer(modifier = Modifier.height(50.dp)) // Increased from 34dp

            // ---- Search bar with NAVIGATION ----
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .clickable {
                        // NAVIGATE TO SEARCH SCREEN
                        navController.navigate("search")
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.search_20px),
                    contentDescription = "Search",
                    modifier = Modifier
                        .size(36.dp)
                        .padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    // Always show placeholder text since this is now just a navigation element
                    Text(
                        text = "Search for Categories",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ---- Quick Analysis title ----
            Text(
                text = "Quick Analysis",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF093030),
                modifier = Modifier.padding(start = 16.dp, bottom = 12.dp)
            )

            // ---- Graph cards with Pager ----
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(horizontal = 16.dp)
            ) { page ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(20.dp))
                        .shadow(4.dp, RoundedCornerShape(20.dp))
                        .background(Color.White),
                    contentAlignment = Alignment.TopStart
                ) {
                    when (page) {
                        0 -> {
                            // First page: Passenger Data Overview
                            PassengerDataOverviewCard()
                        }
                        else -> {
                            // Placeholder for other pages
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Page ${page + 1}",
                                    fontSize = 18.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }

            // ---- Dots Indicator ----
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(3) { index ->
                    val size = animateDpAsState(
                        targetValue = if (pagerState.currentPage == index) 12.dp else 8.dp,
                        label = "dotSize"
                    )

                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(size.value)
                            .clip(RoundedCornerShape(50))
                            .background(
                                if (pagerState.currentPage == index) Color(0xFF052224)
                                else Color.Gray
                            )
                            .clickable {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ---- Recent Activities Header with See All ----
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent Activities",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF052224)
                )

                Text(
                    text = "See All",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF093030),
                    modifier = Modifier.clickable {
                        showAllContainers = !showAllContainers
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ---- Filter Buttons ----
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("Daily", "Weekly", "Monthly").forEach { filter ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(45.dp)
                            .padding(horizontal = 4.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (selectedFilter == filter) Color(0xFF093030) else Color.White
                            )
                            .clickable { selectedFilter = filter },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = filter,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (selectedFilter == filter) Color.White else Color(0xFF093030)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ---- Recent Activities Container with Management Options ----
            Spacer(modifier = Modifier.height(32.dp)) // Additional space to push down

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .padding(24.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Always visible containers
                    ManagementContainer(
                        title = "Taxi Manager",
                        iconRes = R.drawable.taxi_24,
                        onClick = { navController.navigate("passenger_manager") }
                    )

                    ManagementContainer(
                        title = "Add Vehicles",
                        iconRes = R.drawable.taxi_24,
                        onClick = { navController.navigate("add_vehicles") }
                    )

                    ManagementContainer(
                        title = "Announcements",
                        iconRes = R.drawable.advertising_24,
                        onClick = { navController.navigate("reports") }
                    )

                    // Additional containers visible only when "See All" is clicked
                    if (showAllContainers) {
                        ManagementContainer(
                            title = "Bookkeep Records",
                            iconRes = R.drawable.diary_bookmark_down_24,
                            onClick = { navController.navigate("bookkeep_records") }
                        )

                        ManagementContainer(
                            title = "Add Driver",
                            iconRes = R.drawable.driver_man_24,
                            onClick = { navController.navigate("expenses") }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PassengerDataOverviewCard() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        // Header with blue circle and title
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Blue circle before heading
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2196F3)) // Blue color
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Passengers Data Overview",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Main content area
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            // Left side: Big numbers
            Text(
                text = "12,847",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            // Right side: Percentage
            Text(
                text = "+48%",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50) // Light green
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Horizontal divider line
        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Color.Gray.copy(alpha = 0.3f)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Bottom section with icon and comparison
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Small circle container with upward arrow icon
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2196F3)), // Blue background
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_upward_24px),
                    contentDescription = "Arrow Up",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Numbers in blue
            Text(
                text = "9678",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2196F3) // Blue color
            )

            Spacer(modifier = Modifier.width(8.dp))

            // "vs Last month" text
            Text(
                text = "vs Last month",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun ManagementContainer(
    title: String,
    iconRes: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(RoundedCornerShape(35.dp)) // Almost circular edges
            .background(
                Color.White.copy(alpha = 0.7f) // Glass effect with transparency
            )
            .border(
                width = 1.dp,
                color = Color(0xFF093030).copy(alpha = 0.3f), // Subtle border with transparency
                shape = RoundedCornerShape(35.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Left side: Glass circle container WITH ICON
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(
                    Color(0xFF093030).copy(alpha = 0.8f) // Semi-transparent for glass effect
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                modifier = Modifier.size(24.dp)
            )
        }

        // Center: Title text (aligned to the left)
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF093030),
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp) // Left padding for left alignment
        )

        // Right side: Glass rounded container with forward arrow
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp)) // Rounded cubic form
                .background(
                    Color(0xFF093030).copy(alpha = 0.8f) // Semi-transparent for glass effect
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_forward_48px),
                contentDescription = "Forward",
                tint = Color.White.copy(alpha = 0.9f), // Slightly transparent white
                modifier = Modifier.size(20.dp)
            )
        }
    }
}