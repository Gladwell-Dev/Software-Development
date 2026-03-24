package com.example.khulakasi

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun DeleteScreen(navController: NavController) {
    val darkColor = Color(0xFF052224)
    val lightColor = Color(0xFFF1FFF3)
    val containerBg = Color(0xFFDFF7E2)
    val deleteButtonColor = Color(0xFF093030)

    var passwordVisible by remember { mutableStateOf(false) }

    val topDarkHeight = 360.dp
    val lightOffset = 180.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(topDarkHeight)
                .background(darkColor)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = lightOffset)
                .clip(RoundedCornerShape(topStart = 64.dp, topEnd = 64.dp))
                .background(lightColor)
        )

        // 🔙 Back arrow only (no title here anymore)
        Icon(
            painter = painterResource(id = R.drawable.arrow_back_20px),
            contentDescription = "Back",
            tint = lightColor,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 28.dp, start = 16.dp)
                .size(28.dp)
                .clickable { navController.popBackStack() }
        )

        // Delete Account title centered at the top of the arch
        Text(
            text = "Delete Account",
            color = darkColor,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = lightOffset + 16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(top = lightOffset + 60.dp, bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Main question text
            Text(
                text = "Are you sure you want to delete your account?",
                color = darkColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            // Warning container with transparent background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(containerBg.copy(alpha = 0.8f))
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "This action will permanently delete all of your data, and you will not be able to recover it. Please keep the following in mind before proceeding:",
                        color = darkColor,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Text(
                        text = "• All your expenses, income and associated with Bookkeeping will be eliminated.",
                        color = darkColor,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Text(
                        text = "• You will not be able to access your account or any related information.",
                        color = darkColor,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Text(
                        text = "• This action cannot be undone.",
                        color = darkColor,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Password confirmation text
            Text(
                text = "Please enter your password to confirm deletion of your account.",
                color = darkColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Password input container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(containerBg)
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Three dots representing hidden password
                    Text(
                        text = if (passwordVisible) "password123" else "•••••••••••",
                        color = darkColor,
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1f)
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.visibility_24px),
                        contentDescription = "Toggle Password Visibility",
                        tint = darkColor,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { passwordVisible = !passwordVisible }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action buttons row
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Delete Account button
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(deleteButtonColor)
                        .clickable {
                            // Handle delete action
                            navController.popBackStack()
                        }
                        .padding(horizontal = 24.dp, vertical = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Yes, Delete Account",
                        color = lightColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Cancel button
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(containerBg)
                        .clickable {
                            navController.popBackStack()
                        }
                        .padding(horizontal = 24.dp, vertical = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Cancel",
                        color = darkColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}