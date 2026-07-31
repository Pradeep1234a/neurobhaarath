package com.neurobharath.patientmanager.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neurobharath.patientmanager.ui.theme.*
import com.neurobharath.patientmanager.ui.viewmodel.NavigationScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopHeaderBar(
    currentScreen: NavigationScreen,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    userName: String,
    userRole: String,
    onNavigate: (NavigationScreen) -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Surface)
            .border(1.dp, CardBorder)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Brand & Title
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.MedicalServices,
                        contentDescription = "Logo",
                        tint = TextPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = "NEURO BHARATH",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Accent,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = currentScreen.title,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }
            }

            // Quick Nav Links & User Profile
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = { onNavigate(NavigationScreen.LAB_TESTS) },
                    modifier = Modifier
                        .size(36.dp)
                        .background(Background, shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Biotech,
                        contentDescription = "Lab Tests",
                        tint = Accent,
                        modifier = Modifier.size(18.dp)
                    )
                }

                IconButton(
                    onClick = { onNavigate(NavigationScreen.COMMUNICATION) },
                    modifier = Modifier
                        .size(36.dp)
                        .background(Background, shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = "Messages",
                        tint = Primary,
                        modifier = Modifier.size(18.dp)
                    )
                }

                IconButton(
                    onClick = { onNavigate(NavigationScreen.ANALYTICS) },
                    modifier = Modifier
                        .size(36.dp)
                        .background(Background, shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.BarChart,
                        contentDescription = "Analytics",
                        tint = SuccessGreen,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = userName,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = userRole,
                        fontSize = 10.sp,
                        color = TextSecondary
                    )
                }
            }
        }

        // Global Search Bar for Patients / Records
        if (currentScreen == NavigationScreen.PATIENTS || currentScreen == NavigationScreen.DASHBOARD) {
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = {
                    Text("Search patient records by name, ID (e.g. NB-1001), or history...", fontSize = 12.sp, color = TextSecondary)
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = Accent)
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onSearchQueryChange("") }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear", tint = TextSecondary)
                        }
                    }
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Background,
                    unfocusedContainerColor = Background,
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = CardBorder,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )
        }
    }
}
