package com.example.khulakasi

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.navigation.NavController
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlinx.coroutines.delay

data class NotificationData(
    val id: String,
    val title: String,
    val message: String,
    val timestampMillis: Long
)

@SuppressLint("NewApi")
@Composable
fun NotificationsScreen(
    navController: NavController
) {
    val sampleNow = System.currentTimeMillis()
    var notifications by remember {
        mutableStateOf(
            listOf(
                NotificationData(
                    id = "1",
                    title = "Server Maintenance",
                    message = "Planned maintenance tonight from 02:00 — 03:00.",
                    timestampMillis = sampleNow - (2 * 60 * 60 * 1000L)
                ),
                NotificationData(
                    id = "2",
                    title = "New Feature",
                    message = "We added a faster booking flow. Check it out!",
                    timestampMillis = sampleNow - (3 * 24 * 60 * 60 * 1000L)
                ),
                NotificationData(
                    id = "3",
                    title = "Billing Notice",
                    message = "Your invoice for August is now available.",
                    timestampMillis = sampleNow - (18 * 24 * 60 * 60 * 1000L)
                ),
                NotificationData(
                    id = "4",
                    title = "Welcome",
                    message = "Thanks for joining. Here's a quick tour.",
                    timestampMillis = sampleNow - (45 * 24 * 60 * 60 * 1000L)
                )
            )
        )
    }

    var tick by remember { mutableStateOf(0L) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(60_000L)
            tick++
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1FFF3))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Notifications",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF093030)
            )

            IconButton(
                onClick = { navController.popBackStack() }, // UPDATED: Now terminates the screen
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF093030)
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 72.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = notifications.sortedByDescending { it.timestampMillis }, key = { it.id }) { note ->
                NotificationRow(note = note, onClick = {})
                Divider(
                    thickness = 1.dp,
                    color = Color(0xFF052224),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun NotificationRow(note: NotificationData, onClick: () -> Unit) {
    val timestamp = note.timestampMillis
    val timeLabel = formatTimeShort(timestamp)
    val relative = computeRelativeLabel(timestamp)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = note.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF093030)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = note.message,
                    fontSize = 14.sp,
                    color = Color(0xFF333333)
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = timeLabel,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = relative,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF093030)
                )
            }
        }
    }
}

@SuppressLint("NewApi")
private fun formatTimeShort(epochMillis: Long): String {
    val dt = Instant.ofEpochMilli(epochMillis).atZone(ZoneId.systemDefault()).toLocalDateTime()
    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM")
    return "${dt.format(timeFormatter)} • ${dt.format(dateFormatter)}"
}

@SuppressLint("NewApi")
private fun computeRelativeLabel(epochMillis: Long): String {
    val zone = ZoneId.systemDefault()
    val msgDate = Instant.ofEpochMilli(epochMillis).atZone(zone).toLocalDate()
    val today = LocalDate.now(zone)
    val days = ChronoUnit.DAYS.between(msgDate, today)

    return when {
        days == 0L -> "Today"
        days in 1..7 -> "Last Week"
        days in 8..30 -> "Last Month"
        days in 31..365 -> "Older"
        else -> msgDate.format(DateTimeFormatter.ofPattern("MMM d, yyyy"))
    }
}