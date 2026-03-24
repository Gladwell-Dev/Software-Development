package com.example.khulakasi

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.border
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun AccountScreen(navController: NavController) {
    val backgroundDark = Color(0xFF0E3E3E)
    val circleBg = Color(0xFFF1FFF3)
    val iconTint = Color(0xFF093030)
    val labelColor = Color.White
    val context = LocalContext.current // Move this outside the lambda

    var showLogoutDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundDark)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Heading
            Text(
                text = "Settings",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Settings
            ExpandableSettingRow(
                iconRes = R.drawable.key_vertical_40px,
                label = "Password Settings",
                circleBg = circleBg,
                iconTint = iconTint,
                labelColor = Color(0xFF093030), // UPDATED: Changed to 093030
                isPasswordRow = true
            )

            // Notifications Settings
            ExpandableSettingRow(
                iconRes = R.drawable.circle_notifications_24px,
                label = "Notifications Settings",
                circleBg = circleBg,
                iconTint = iconTint,
                labelColor = Color(0xFF093030), // UPDATED: Changed to 093030
                isNotificationRow = true
            )

            // Address Settings
            ExpandableSettingRow(
                iconRes = R.drawable.distance_40px,
                label = "Address Settings",
                circleBg = circleBg,
                iconTint = iconTint,
                labelColor = Color(0xFF093030), // UPDATED: Changed to 093030
                isAddressRow = true
            )

            // Delete Account
            NonExpandableForwardRow(
                iconRes = R.drawable.person_24px,
                label = "Delete Account",
                circleBg = circleBg,
                iconTint = iconTint,
                labelColor = Color(0xFF093030), // UPDATED: Changed to 093030
                onClick = { navController.navigate("delete") }
            )

            // LogOut Row - UPDATED: Shows popup instead of expanding
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFF1FFF3)) // UPDATED: Container background to F1FFF3
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .clickable { showLogoutDialog = true }, // UPDATED: Show dialog instead of expand
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(circleBg)
                        .border(2.dp, Color(0xFF093030), CircleShape), // UPDATED: Added border
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.logout_48px),
                        contentDescription = "Logout",
                        tint = iconTint,
                        modifier = Modifier.size(26.dp)
                    )
                }

                Spacer(modifier = Modifier.width(18.dp))

                Text(
                    text = "LogOut",
                    color = Color(0xFF093030), // UPDATED: Changed to 093030
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    painter = painterResource(id = R.drawable.arrow_drop_down_circle_40px),
                    contentDescription = "Logout",
                    tint = Color.Gray,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        // UPDATED: End Session Pop-up Dialog
        if (showLogoutDialog) {
            Dialog(
                onDismissRequest = { showLogoutDialog = false },
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                )
            ) {
                EndSessionPopup(
                    onConfirm = {
                        Toast.makeText(context, "Session Ended", Toast.LENGTH_SHORT).show()
                        showLogoutDialog = false
                    },
                    onCancel = { showLogoutDialog = false }
                )
            }
        }
    }
}

// UPDATED: Small centered pop-up for End Session
@Composable
fun EndSessionPopup(
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFF1FFF3))
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "End Session",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0E3E3E)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Are you sure you want to logout?",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Confirm Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF093030))
                    .clickable { onConfirm() }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Yes, End Session",
                    color = Color(0xFFF1FFF3),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Cancel Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFDFF7E2))
                    .clickable { onCancel() }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Cancel",
                    color = Color(0xFF0E3E3E),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ExpandableSettingRow(
    iconRes: Int,
    label: String,
    circleBg: Color,
    iconTint: Color,
    labelColor: Color,
    isPasswordRow: Boolean = false,
    isNotificationRow: Boolean = false,
    isAddressRow: Boolean = false
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFF1FFF3)) // UPDATED: Container background to F1FFF3
            .clickable { expanded = !expanded }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Row header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(circleBg)
                    .border(2.dp, Color(0xFF093030), CircleShape), // UPDATED: Added border
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = label,
                    tint = iconTint,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(18.dp))

            Text(
                text = label,
                color = labelColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Icon(
                painter = painterResource(id = R.drawable.arrow_drop_down_circle_40px),
                contentDescription = "Expand",
                tint = Color.Gray,
                modifier = Modifier.size(28.dp)
            )
        }

        // Expanded content
        if (expanded) {
            Spacer(modifier = Modifier.height(12.dp))
            when {
                isPasswordRow -> ChangePinSection()
                isNotificationRow -> NotificationSettingsSection()
                isAddressRow -> AddAddressSection()
            }
        }
    }

    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
fun NonExpandableForwardRow(
    iconRes: Int,
    label: String,
    circleBg: Color,
    iconTint: Color,
    labelColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFF1FFF3)) // UPDATED: Container background to F1FFF3
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(circleBg)
                .border(2.dp, Color(0xFF093030), CircleShape), // UPDATED: Added border
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = label,
                tint = iconTint,
                modifier = Modifier.size(26.dp)
            )
        }

        Spacer(modifier = Modifier.width(18.dp))

        Text(
            text = label,
            color = labelColor,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        Icon(
            painter = painterResource(id = R.drawable.arrow_forward_48px),
            contentDescription = "Go",
            tint = Color.Gray,
            modifier = Modifier.size(28.dp)
        )
    }

    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
fun ChangePinSection() {
    var currentPin by remember { mutableStateOf("") }
    var newPin by remember { mutableStateOf("") }
    var confirmPin by remember { mutableStateOf("") }

    var currentPinVisible by remember { mutableStateOf(false) }
    var newPinVisible by remember { mutableStateOf(false) }
    var confirmPinVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Text(
        text = "Change Pin",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF093030),
        modifier = Modifier.padding(bottom = 12.dp)
    )

    // UPDATED: Rounded input fields
    PinInputField("Current Pin", currentPin, { currentPin = it }, currentPinVisible) { currentPinVisible = !currentPinVisible }
    Spacer(modifier = Modifier.height(12.dp))
    PinInputField("New Pin", newPin, { newPin = it }, newPinVisible) { newPinVisible = !newPinVisible }
    Spacer(modifier = Modifier.height(12.dp))
    PinInputField("Confirm Pin", confirmPin, { confirmPin = it }, confirmPinVisible) { confirmPinVisible = !confirmPinVisible }

    Spacer(modifier = Modifier.height(20.dp))

    // UPDATED: Small button (not too wide)
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .clip(RoundedCornerShape(20.dp)) // UPDATED: More rounded edges
            .background(Color(0xFF093030))
            .clickable {
                Toast.makeText(context, "Pin Changed Successfully", Toast.LENGTH_SHORT).show()
            }
            .padding(horizontal = 32.dp, vertical = 12.dp), // UPDATED: Smaller padding
        contentAlignment = Alignment.Center
    ) {
        Text("Change Pin", color = Color(0xFFF1FFF3), fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun AddAddressSection() {
    var street by remember { mutableStateOf("") }
    var suburb by remember { mutableStateOf("") }
    var region by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var province by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }

    val context = LocalContext.current

    Text(
        text = "Add Address",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF093030),
        modifier = Modifier.padding(bottom = 12.dp)
    )

    // UPDATED: All input fields with rounded edges
    AddressInputField("Street Name", street) { street = it }
    Spacer(modifier = Modifier.height(12.dp))
    AddressInputField("Suburb/Rural", suburb) { suburb = it }
    Spacer(modifier = Modifier.height(12.dp))
    AddressInputField("Region", region) { region = it }
    Spacer(modifier = Modifier.height(12.dp))
    AddressInputField("City", city) { city = it }
    Spacer(modifier = Modifier.height(12.dp))
    AddressInputField("Province", province) { province = it }
    Spacer(modifier = Modifier.height(12.dp))
    AddressInputField("Code", code) { code = it }

    Spacer(modifier = Modifier.height(20.dp))

    // UPDATED: Small button (not too wide)
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .clip(RoundedCornerShape(20.dp)) // UPDATED: More rounded edges
            .background(Color(0xFF093030))
            .clickable {
                Toast.makeText(context, "Address Saved Successfully", Toast.LENGTH_SHORT).show()
            }
            .padding(horizontal = 32.dp, vertical = 12.dp), // UPDATED: Smaller padding
        contentAlignment = Alignment.Center
    ) {
        Text("Save Address", color = Color(0xFFF1FFF3), fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun AddressInputField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column {
        Text(label, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF093030), modifier = Modifier.padding(bottom = 6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF093030),
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color(0xFF093030)
            ),
            shape = RoundedCornerShape(20.dp) // UPDATED: More rounded edges
        )
    }
}

@Composable
fun PinInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    visible: Boolean,
    onVisibilityToggle: () -> Unit
) {
    Column {
        Text(label, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF093030), modifier = Modifier.padding(bottom = 6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.visibility_24px),
                    contentDescription = "Toggle Visibility",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onVisibilityToggle() },
                    tint = Color(0xFF093030)
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF093030),
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color(0xFF093030)
            ),
            shape = RoundedCornerShape(20.dp) // UPDATED: More rounded edges
        )
    }
}

@Composable
fun NotificationSettingsSection() {
    var adminAnnouncements by remember { mutableStateOf(true) }
    var systemMaintenance by remember { mutableStateOf(false) }

    Text(
        text = "Notification Preferences",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF093030),
        modifier = Modifier.padding(bottom = 12.dp)
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
    ) {
        androidx.compose.material3.Switch(
            checked = adminAnnouncements,
            onCheckedChange = { adminAnnouncements = it }
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text("Admin Announcements", fontSize = 16.sp, color = Color(0xFF093030))
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
    ) {
        androidx.compose.material3.Switch(
            checked = systemMaintenance,
            onCheckedChange = { systemMaintenance = it }
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text("System Maintenance Alerts", fontSize = 16.sp, color = Color(0xFF093030))
    }
}