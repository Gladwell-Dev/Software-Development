package com.example.khulakasi

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(
    navController: NavController,
    onGoogleSignInClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("Select Role") }
    var expanded by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }
    val context = LocalContext.current

    val borderColor = Color(0xFF093030).copy(alpha = 0.5f) // glass-like effect

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(modifier = Modifier.height(12.dp))

            // Profile Icon
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile Icon",
                tint = Color(0xFF093030),
                modifier = Modifier
                    .size(80.dp)
                    .padding(top = 20.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "Sign To Your Account",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                "Enter You Details Here",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(30.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { /* Apple Sign In */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDFF7E2)),
                        shape = RoundedCornerShape(30.dp),
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 6.dp)
                            .border(1.dp, borderColor, RoundedCornerShape(30.dp)) // border added
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.apple),
                            contentDescription = "Apple Icon",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Continue with Apple", color = Color.Black)
                    }

                    Button(
                        onClick = onGoogleSignInClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDFF7E2)),
                        shape = RoundedCornerShape(30.dp),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 6.dp)
                            .border(1.dp, borderColor, RoundedCornerShape(30.dp)) // border added
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.myicon),
                            contentDescription = "Google Sign-In",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Continue with Google", color = Color.Black)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Email field
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Email Address*",
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    leadingIcon = { Icon(Icons.Default.Email, null) },
                    shape = RoundedCornerShape(30.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0x33FFFFFF),
                        unfocusedContainerColor = Color(0x22FFFFFF),
                        disabledContainerColor = Color(0x22FFFFFF),
                        errorContainerColor = Color(0x22FFFFFF),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, borderColor, RoundedCornerShape(30.dp)), // border added
                    placeholder = { Text("Enter your email") }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Role dropdown (unchanged)
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Role*",
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                )
                Box {
                    OutlinedTextField(
                        value = role,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                Icons.Default.ArrowDropDown,
                                null,
                                Modifier.clickable { expanded = true }
                            )
                        },
                        shape = RoundedCornerShape(30.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFDFF7E2),
                            unfocusedContainerColor = Color(0xFFDFF7E2),
                            disabledContainerColor = Color(0xFFDFF7E2),
                            errorContainerColor = Color(0xFFDFF7E2),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        listOf("Admin", "Taxi Owner", "Manager").forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    role = it
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Password (unchanged)
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Password*",
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    leadingIcon = { Icon(Icons.Default.Lock, null) },
                    placeholder = { Text("8+ Characters, 1 Uppercase, 1 Letter") },
                    shape = RoundedCornerShape(30.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFDFF7E2),
                        unfocusedContainerColor = Color(0xFFDFF7E2),
                        disabledContainerColor = Color(0xFFDFF7E2),
                        errorContainerColor = Color(0xFFDFF7E2),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Sign In button: navigate to Main on success
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF093030)),
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        val auth = FirebaseAuth.getInstance()
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
                                    navController.navigate(Screen.Main.route) {
                                        popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                                        launchSingleTop = true
                                    }
                                } else {
                                    error = task.exception?.message ?: "Login failed"
                                }
                            }
                    } else {
                        error = "Please enter email and password"
                    }
                }
            ) {
                Text("Sign In", color = Color.White)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Go to SignUp screen
            TextButton(onClick = {
                navController.navigate("SignUp") {
                    popUpTo(0)
                }
            }) {
                Text("Don't Have An Account? Sign Up", color = Color(0xFF093030))
            }
            TextButton(onClick = {
                navController.navigate("forgot_password")
            }) {
                Text("Forgot Password?", color = Color(0xFF093030))
            }

            if (error.isNotEmpty()) {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }
        }
    }

}