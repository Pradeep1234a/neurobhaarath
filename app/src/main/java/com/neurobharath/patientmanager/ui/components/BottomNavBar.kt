package com.neurobharath.patientmanager.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neurobharath.patientmanager.ui.theme.*
import com.neurobharath.patientmanager.ui.viewmodel.NavigationScreen

data class NavItem(
    val screen: NavigationScreen,
    val icon: ImageVector,
    val label: String
)

@Composable
fun BottomNavBar(
    currentScreen: NavigationScreen,
    onNavigate: (NavigationScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavItem(NavigationScreen.DASHBOARD, Icons.Default.Dashboard, "Dashboard"),
        NavItem(NavigationScreen.PATIENTS, Icons.Default.People, "Patients"),
        NavItem(NavigationScreen.APPOINTMENTS, Icons.Default.Event, "Schedule"),
        NavItem(NavigationScreen.MEDICATIONS, Icons.Default.Medication, "Medications"),
        NavItem(NavigationScreen.ANALYTICS, Icons.Default.Assessment, "Analytics")
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Surface)
            .border(1.dp, CardBorder)
            .padding(vertical = 8.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            val isSelected = currentScreen == item.screen
            val activeColor = if (isSelected) Primary else TextSecondary

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onNavigate(item.screen) }
                    .padding(vertical = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            if (isSelected) Primary.copy(alpha = 0.18f) else Background.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 14.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = activeColor,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = item.label,
                    fontSize = 11.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = activeColor
                )
            }
        }
    }
}
