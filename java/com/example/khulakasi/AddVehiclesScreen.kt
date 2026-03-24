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

data class Vehicle(
    val registration: String,
    val sits: String,
    val make: String,
    /* val model: String,
     val year: String,*/
    /*    val RankingLocation: String
    val RankingType: String
*/


)

@Composable
fun AddVehiclesScreen(
    navController: NavController,       // <-- NavController added
    vehicles: List<Vehicle>,
    onAddVehicle: (Vehicle) -> Unit
) {
    var registration by remember { mutableStateOf("") }
    var sits by remember { mutableStateOf("") }
    var make by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text("Add Vehicle", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = registration,
            onValueChange = { registration = it },
            label = { Text("Registration number*") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        OutlinedTextField(
            value = sits,
            onValueChange = { sits = it },
            label = { Text("Number of sits* ") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        OutlinedTextField(
            value = make,
            onValueChange = { make = it },
            label = { Text("Make*") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        // Add Driver Button
        Button(
            onClick = {
                if (registration.isNotEmpty() && sits.isNotEmpty() && make.isNotEmpty()) {
                    onAddVehicle(Vehicle(registration, sits, make))
                    registration = ""
                    sits = ""
                    make = ""
                    // Example: Navigate after adding driver
                    // navController.navigate(Screen.Dashboard.route)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF093030)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Add Vehicle", color = Color.White)
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

        Text("Available Vehicles", style = MaterialTheme.typography.titleMedium)

        if (vehicles.isEmpty()) {
            Text("No Vehicles added yet.", style = MaterialTheme.typography.bodyMedium)
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                vehicles.forEach { vehicle ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF6F6F6))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(vehicle.registration, style = MaterialTheme.typography.titleMedium)
                            Text("Phone: ${vehicle.sits}")
                            Text("Licence: ${vehicle.make}")
                        }
                    }
                }
            }
        }
    }
}