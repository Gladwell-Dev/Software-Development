package com.example.khulakasi

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

data class Passenger(
    val name: String,
    val phone: String,
    val kin: String,
    val kinPhone: String
)

@Composable
fun ManagerScreen(navController: NavHostController) {
    var driverName by remember { mutableStateOf("") }
    var taxiReg by remember { mutableStateOf("") }
    var departure by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }

    var passengerName by remember { mutableStateOf("") }
    var passengerPhone by remember { mutableStateOf("") }
    var kinName by remember { mutableStateOf("") }
    var kinPhone by remember { mutableStateOf("") }

    var selectedSeats by remember { mutableStateOf(15) }
    val passengers = remember { mutableStateListOf<Passenger>() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Top Bar
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back_20px),
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }

                Text(
                    text = "Taxi Manager",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                IconButton(
                    onClick = { /* notifications */ },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.circle_notifications_24px),
                        contentDescription = "Notifications",
                        tint = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("Vehicle Details", fontWeight = FontWeight.Bold, fontSize = 16.sp)

            Spacer(modifier = Modifier.height(12.dp))

            // Vehicle details
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                InputField("Driver Name", driverName, { driverName = it }, modifier = Modifier.weight(1f))
                InputField("Taxi Reg", taxiReg, { taxiReg = it }, modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                InputField("Departure", departure, onChange = { departure = it }, modifier = Modifier.weight(1f))
                InputField("Destination", destination, onChange = { destination = it }, modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Seats toggle
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SeatButton("15 Sits", 15, selectedSeats) { selectedSeats = it }
                SeatButton("22 Sits", 22, selectedSeats) { selectedSeats = it }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Passenger details
            Text("Passenger Details", fontWeight = FontWeight.Bold, fontSize = 16.sp)

            Spacer(modifier = Modifier.height(12.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                InputField("Passenger", passengerName, onChange = { passengerName = it }, modifier = Modifier.weight(1f))
                InputField("Phone", passengerPhone, onChange = { passengerPhone = it }, modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                InputField("Related Or Kin", kinName, onChange = { kinName = it }, modifier = Modifier.weight(1f))
                InputField("Phone", kinPhone, onChange = { kinPhone = it }, modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Save Button
            Button(
                onClick = {
                    if (passengers.size < selectedSeats && passengerName.isNotBlank()) {
                        passengers.add(
                            Passenger(passengerName, passengerPhone, kinName, kinPhone)
                        )
                        passengerName = ""
                        passengerPhone = ""
                        kinName = ""
                        kinPhone = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF093030)),
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Save", color = Color.White, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Records
            Text("Records", fontWeight = FontWeight.Bold, fontSize = 18.sp)

            passengers.forEachIndexed { index, passenger ->
                var expanded by remember { mutableStateOf(false) }

                val shape = RoundedCornerShape(16.dp)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .border(1.dp, Color(0xFF0E3E3E), shape = shape)
                        .padding(12.dp)
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("${index + 1}. ${passenger.name}  ${passenger.phone}")
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = null
                            )
                        }
                    }

                    if (expanded) {
                        Column(Modifier.padding(start = 12.dp)) {
                            Text("Kin: ${passenger.kin}")
                            Text("Kin Phone: ${passenger.kinPhone}")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Statements Container
            val containerShape = RoundedCornerShape(16.dp)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .border(1.dp, Color(0xFF0E3E3E), shape = containerShape)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text("Statements", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(10.dp))

                repeat(10) {
                    Text("Statement item ${it + 1}", color = Color.Gray, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }
}

@Composable
fun InputField(label: String, value: String, onChange: (String) -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(label, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.Black)

        OutlinedTextField(
            value = value,
            onValueChange = onChange,
            shape = RoundedCornerShape(28.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFDFF7E2),
                unfocusedContainerColor = Color(0xFFDFF7E2),
                focusedBorderColor = Color(0xFF0E3E3E),
                unfocusedBorderColor = Color(0xFF0E3E3E),
                cursorColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )
    }
}

@Composable
fun SeatButton(label: String, seats: Int, selectedSeats: Int, onClick: (Int) -> Unit) {
    val shape = RoundedCornerShape(20.dp)
    Box(
        modifier = Modifier
            .background(
                if (selectedSeats == seats) Color(0xFF093030) else Color(0xFFDFF7E2),
                shape = shape
            )
            .border(1.dp, Color(0xFF0E3E3E), shape = shape)
            .clickable { onClick(seats) }
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Text(
            text = label,
            color = if (selectedSeats == seats) Color.White else Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}
