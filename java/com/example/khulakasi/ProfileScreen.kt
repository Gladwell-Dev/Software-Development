package com.example.khulakasi

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

@Composable
fun ProfileScreen(navController: NavController) {
    val topDarkHeight = 260.dp
    val lightOffset = 200.dp

    val darkColor = Color(0xFF052224)
    val lightColor = Color(0xFFF1FFF3)
    val containerBg = Color(0xFF093030)

    var expandProfile by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightColor)
            .verticalScroll(scrollState)
    ) {
        // Dark top band
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(topDarkHeight)
                .background(darkColor)
        )

        // Light rounded block
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = lightOffset)
                .clip(RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp))
                .background(lightColor)
                .padding(horizontal = 24.dp, vertical = 32.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(120.dp))

                // Edit Profile row (expand / collapse)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expandProfile = !expandProfile },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(containerBg),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.person_24px),
                            contentDescription = "Profile Icon",
                            tint = Color.White,
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(18.dp))

                    Text(
                        text = "Edit Profile",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.arrow_drop_down_circle_40px),
                        contentDescription = "Expand",
                        tint = Color.Gray,
                        modifier = Modifier.size(28.dp)
                    )
                }

                // Expand Section
                if (expandProfile) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                            .padding(16.dp)
                    ) {
                        ProfileInputContainer(title = "Username", placeholder = "E.g Oops Makgopa")
                        Spacer(modifier = Modifier.height(12.dp))
                        ProfileInputContainer(title = "Phone Number", placeholder = "+27 098765432")
                        Spacer(modifier = Modifier.height(12.dp))
                        ProfileInputContainer(title = "Email Address", placeholder = "ngomane568@gmail.com")
                        Spacer(modifier = Modifier.height(12.dp))
                        ProfileInputContainer(title = "Identity Number", placeholder = "0409823623085")
                        Spacer(modifier = Modifier.height(16.dp))

                        // Upload Image Box
                        Text(
                            text = "Upload Image",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = containerBg,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        ) {
                            Canvas(modifier = Modifier.matchParentSize()) {
                                val stroke = Stroke(
                                    width = 3f,
                                    cap = StrokeCap.Round,
                                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f)
                                )
                                drawRoundRect(
                                    color = containerBg,
                                    style = stroke,
                                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(24f, 24f)
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.cloud_upload_40px),
                                    contentDescription = "Upload",
                                    tint = containerBg,
                                    modifier = Modifier.size(40.dp)
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "Choose a file or Drag and Drop it here",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = Color.DarkGray
                                )

                                Text(
                                    text = "JPEG & PNG",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(containerBg)
                                        .padding(horizontal = 20.dp, vertical = 10.dp)
                                ) {
                                    Text(
                                        text = "Browse File",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Update Profile Button
                        Box(
                            modifier = Modifier
                                .width(180.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(containerBg)
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Update Profile",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Security
                ProfileOptionRow(
                    iconId = R.drawable.security_24px,
                    label = "Security",
                    containerBg = containerBg,
                    onClick = {
                        navController.navigate(Screen.Settings.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Settings
                ProfileOptionRow(
                    iconId = R.drawable.settings_20px,
                    label = "Settings",
                    containerBg = containerBg,
                    onClick = {
                        navController.navigate(Screen.Settings.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Logout
                ProfileOptionRow(
                    iconId = R.drawable.logout_24px,
                    label = "Logout",
                    containerBg = containerBg,
                    onClick = {
                        navController.navigate(Screen.Settings.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }

        // Back Arrow
        // In ProfileScreen, replace the back arrow section with:
        Icon(
            painter = painterResource(id = R.drawable.arrow_back_20px),
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 24.dp)
                .size(28.dp)
                .clickable {
                    navController.popBackStack() // UPDATED: Now terminates the screen
                }
        )

        // Heading
        Text(
            text = "Profile",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 28.dp)
        )

        // Profile picture + name + ID
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 130.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(Color.Gray.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Text("+", fontSize = 40.sp, color = Color.DarkGray)
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Ngomane Gladwell",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = darkColor
            )

            Text(
                text = "ID: 0409823623085",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.DarkGray
            )
        }
    }
}

@Composable
fun ProfileOptionRow(iconId: Int, label: String, containerBg: Color, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .background(containerBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = label,
                tint = Color.White,
                modifier = Modifier.size(26.dp)
            )
        }

        Spacer(modifier = Modifier.width(18.dp))

        Text(
            text = label,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )

        // Trailing arrow is not clickable by itself
        Icon(
            painter = painterResource(id = R.drawable.arrow_forward_48px),
            contentDescription = "Next",
            tint = Color.Gray,
            modifier = Modifier.size(28.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileInputContainer(title: String, placeholder: String) {
    var text by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text(text = placeholder, fontSize = 13.sp, color = Color.Gray) },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFDFF7E2),
                unfocusedContainerColor = Color(0xFFDFF7E2),
                disabledContainerColor = Color(0xFFDFF7E2),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                disabledTextColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
        )
    }
}
