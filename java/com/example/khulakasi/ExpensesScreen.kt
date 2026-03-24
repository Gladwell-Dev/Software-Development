package com.example.khulakasi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class Driver(
    val fullName: String,
    val phone: String,
    val licence: String
)

@Composable
fun ExpensesScreen(
    navController: NavController,       // <-- NavController added
    drivers: List<Driver>,
    onAddDriver: (Driver) -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var licence by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text("Add Driver", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        OutlinedTextField(
            value = licence,
            onValueChange = { licence = it },
            label = { Text("Licence Number") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        // Add Driver Button
        Button(
            onClick = {
                if (fullName.isNotEmpty() && phone.isNotEmpty() && licence.isNotEmpty()) {
                    onAddDriver(Driver(fullName, phone, licence))
                    fullName = ""
                    phone = ""
                    licence = ""
                    // Example: Navigate after adding driver
                    // navController.navigate(Screen.Dashboard.route)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF093030)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Add Driver", color = Color.White)
        }

        // Back button
        Button(
            onClick = { navController.popBackStack() },   // <-- Uses NavController
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Back", color = Color.White)
        }

        Text("Available Drivers", style = MaterialTheme.typography.titleMedium)

        if (drivers.isEmpty()) {
            Text("No drivers added yet.", style = MaterialTheme.typography.bodyMedium)
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                drivers.forEach { driver ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF6F6F6))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(driver.fullName, style = MaterialTheme.typography.titleMedium)
                            Text("Phone: ${driver.phone}")
                            Text("Licence: ${driver.licence}")
                        }
                    }
                }
            }
        }
    }
}
