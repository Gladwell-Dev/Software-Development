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
fun AddExpensesScreen(
    navController: NavController, // <-- Added NavController
    vehicles: List<String>,
    expenses: List<String>,
    onSave: (String, String, String, String) -> Unit
) {
    var selectedVehicle by remember { mutableStateOf("") }
    var selectedExpense by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    var vehicleExpanded by remember { mutableStateOf(false) }
    var expenseExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFDFF7E2))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Record Daily Expenses",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )

        // Vehicle dropdown
        ExposedDropdownMenuBox(
            expanded = vehicleExpanded,
            onExpandedChange = { vehicleExpanded = !vehicleExpanded }
        ) {
            OutlinedTextField(
                value = selectedVehicle,
                onValueChange = {},
                label = { Text("Vehicle Reg") },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = true,
                shape = RoundedCornerShape(16.dp),
                trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null) }
            )
            ExposedDropdownMenu(
                expanded = vehicleExpanded,
                onDismissRequest = { vehicleExpanded = false }
            ) {
                vehicles.forEach { vehicle ->
                    DropdownMenuItem(
                        text = { Text(vehicle) },
                        onClick = {
                            selectedVehicle = vehicle
                            vehicleExpanded = false
                        }
                    )
                }
            }
        }

        // Driver dropdown
        ExposedDropdownMenuBox(
            expanded = expenseExpanded,
            onExpandedChange = { expenseExpanded = !expenseExpanded }
        ) {
            OutlinedTextField(
                value = selectedExpense,
                onValueChange = {},
                label = { Text("Enter Daily Expenses") },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = true,
                shape = RoundedCornerShape(16.dp),
                trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null) }
            )
            ExposedDropdownMenu(
                expanded = expenseExpanded,
                onDismissRequest = { expenseExpanded = false }
            ) {
                expenses.forEach { expense ->
                    DropdownMenuItem(
                        text = { Text(expense) },
                        onClick = {
                            selectedExpense = expense
                            expenseExpanded = false
                        }
                    )
                }
            }
        }

        // Amount
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Total Expenses Amount (R)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(16.dp)
        )

        // Notes
        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("Notes (optional)") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )

        // Save Record Button
        Button(
            onClick = {
                if (selectedVehicle.isNotEmpty() && selectedExpense.isNotEmpty() && amount.isNotEmpty()) {
                    onSave(selectedVehicle, selectedExpense, amount, notes)
                    // Example navigation after save (optional)
                    // navController.navigate(Screen.Dashboard.route)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF093030)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text("Save Record", color = Color.White)
        }

        // Back Button
        Button(
            onClick = { navController.popBackStack() },  // <-- Uses NavController
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text("Back", color = Color.White)
        }
    }
}