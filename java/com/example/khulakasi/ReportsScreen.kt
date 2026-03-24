package com.example.khulakasi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(
    navController: NavController,
    announcements: List<Announcement> = listOf(),
    onCreate: (String, String, String, String) -> Unit = { _, _, _, _ -> }
) {
    var title by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("General") }
    var priority by remember { mutableStateOf("Normal") }

    var categoryExpanded by remember { mutableStateOf(false) }
    var priorityExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Create Announcement",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Announcement Title") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )

        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Announcement Message") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            shape = RoundedCornerShape(16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Category Dropdown
            ExposedDropdownMenuBox(
                expanded = categoryExpanded,
                onExpandedChange = { categoryExpanded = !categoryExpanded },
                modifier = Modifier.weight(1f)
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = {},
                    label = { Text("Category") },
                    readOnly = true,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null) }
                )
                ExposedDropdownMenu(
                    expanded = categoryExpanded,
                    onDismissRequest = { categoryExpanded = false }
                ) {
                    listOf("General", "Urgent", "Reminder").forEach {
                        DropdownMenuItem(text = { Text(it) }, onClick = {
                            category = it
                            categoryExpanded = false
                        })
                    }
                }
            }

            // Priority Dropdown
            ExposedDropdownMenuBox(
                expanded = priorityExpanded,
                onExpandedChange = { priorityExpanded = !priorityExpanded },
                modifier = Modifier.weight(1f)
            ) {
                OutlinedTextField(
                    value = priority,
                    onValueChange = {},
                    label = { Text("Priority") },
                    readOnly = true,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null) }
                )
                ExposedDropdownMenu(
                    expanded = priorityExpanded,
                    onDismissRequest = { priorityExpanded = false }
                ) {
                    listOf("Normal", "High", "Critical").forEach {
                        DropdownMenuItem(text = { Text(it) }, onClick = {
                            priority = it
                            priorityExpanded = false
                        })
                    }
                }
            }
        }

        Button(
            onClick = {
                if (title.isNotEmpty() && message.isNotEmpty()) {
                    onCreate(title, message, category, priority)
                    title = ""
                    message = ""
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF093030)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text("Create Announcement", color = Color.White)
        }

        // Navigate back button
        Button(
            onClick = { navController.navigate(Screen.Dashboard.route) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text("Back to Dashboard", color = Color.White)
        }

        // List recent announcements
        if (announcements.isNotEmpty()) {
            Text("Recent Announcements", style = MaterialTheme.typography.titleMedium, color = Color.Black)
            announcements.forEach { ann ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(ann.title, style = MaterialTheme.typography.titleSmall)
                        Text(ann.message, style = MaterialTheme.typography.bodySmall)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(ann.priority, style = MaterialTheme.typography.bodySmall)
                            Text(ann.date, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}

// Data class
data class Announcement(
    val title: String,
    val message: String,
    val category: String,
    val priority: String,
    val date: String
)
