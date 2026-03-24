package com.example.khulakasi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchScreen() {
    // Color scheme
    val darkColor = Color(0xFF052224)
    val lightColor = Color(0xFFF1FFF3)
    val textColor = Color(0xFF031314)
    val buttonColor = Color(0xFF093030)
    val buttonTextColor = Color(0xFFF1FFF3)

    // State variables
    var selectedCategory by remember { mutableStateOf("Select the Categories") }
    var selectedDate by remember { mutableStateOf("Select Date") }
    var selectedReport by remember { mutableStateOf("Records") }
    var showCategoryDropdown by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    val categories = listOf("Passengers", "Income", "Expenses", "Drivers", "Announcements")

    // Layout dimensions
    val topDarkHeight = 280.dp
    val lightOffset = 220.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightColor)
    ) {
        // Dark top band
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(topDarkHeight)
                .background(darkColor)
        )

        // Light block with rounded top corners
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = lightOffset)
                .clip(RoundedCornerShape(topStart = 64.dp, topEnd = 64.dp))
                .background(lightColor)
        )

        // Notifications Icon (top-right corner)
        Image(
            painter = painterResource(id = R.drawable.circle_notifications_24px),
            contentDescription = "Notifications",
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 16.dp, top = 24.dp)
                .size(28.dp)
                .clickable { /* Handle notifications click */ }
        )

        // Header centered at the very top
        Text(
            text = "Search",
            color = lightColor,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 28.dp)
        )

        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(top = lightOffset + 32.dp, bottom = 100.dp)
        ) {
            // Categories Section
            Text(
                text = "Categories",
                color = textColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Categories Dropdown Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp) // reduced height
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(28.dp)
                    )
                    .clickable { showCategoryDropdown = true }
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedCategory,
                        color = if (selectedCategory == "Select the Categories") Color.Gray else textColor,
                        fontSize = 16.sp
                    )

                    Image(
                        painter = painterResource(id = R.drawable.arrow_drop_down_circle_40px),
                        contentDescription = "Dropdown",
                        colorFilter = ColorFilter.tint(buttonColor), // updated to 093030
                        modifier = Modifier.size(24.dp)
                    )
                }

                if (showCategoryDropdown) {
                    DropdownMenu(
                        expanded = showCategoryDropdown,
                        onDismissRequest = { showCategoryDropdown = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category) },
                                onClick = {
                                    selectedCategory = category
                                    showCategoryDropdown = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Date Section
            Text(
                text = "Date",
                color = textColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Date Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp) // reduced height
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(28.dp)
                    )
                    .clickable { showDatePicker = true }
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedDate,
                        color = if (selectedDate == "Select Date") Color.Gray else textColor,
                        fontSize = 16.sp
                    )

                    Image(
                        painter = painterResource(id = R.drawable.calendar_month_24px),
                        contentDescription = "Calendar",
                        colorFilter = ColorFilter.tint(buttonColor), // updated to 093030
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                showDatePicker = true
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Reports Section
            Text(
                text = "Reports",
                color = textColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Reports Options Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        selectedReport = "Records"
                    }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.brightness_1_48px),
                        contentDescription = "Records Option",
                        colorFilter = ColorFilter.tint(
                            if (selectedReport == "Records") darkColor else Color.Gray
                        ),
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Records",
                        color = textColor,
                        fontSize = 16.sp,
                        fontWeight = if (selectedReport == "Records") FontWeight.SemiBold else FontWeight.Normal
                    )
                }

                Spacer(modifier = Modifier.width(24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        selectedReport = "Other"
                    }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.album_48px),
                        contentDescription = "Other Option",
                        colorFilter = ColorFilter.tint(
                            if (selectedReport == "Other") darkColor else Color.Gray
                        ),
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Other",
                        color = textColor,
                        fontSize = 16.sp,
                        fontWeight = if (selectedReport == "Other") FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // Search Button (smaller)
            Button(
                onClick = {
                    // Handle search logic
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp), // reduced button size
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "Search",
                    color = buttonTextColor,
                    fontSize = 16.sp, // slightly smaller font
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
