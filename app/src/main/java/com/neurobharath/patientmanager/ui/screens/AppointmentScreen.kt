package com.neurobharath.patientmanager.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neurobharath.patientmanager.data.local.entity.AppointmentEntity
import com.neurobharath.patientmanager.ui.components.StatusBadge
import com.neurobharath.patientmanager.ui.theme.*

@Composable
fun AppointmentScreen(
    appointments: List<AppointmentEntity>,
    onAddAppointmentClick: () -> Unit,
    onStatusChange: (AppointmentEntity, String) -> Unit
) {
    var selectedFilter by remember { mutableStateOf("All") }

    val filteredAppointments = when (selectedFilter) {
        "Scheduled" -> appointments.filter { it.status == "Scheduled" }
        "Completed" -> appointments.filter { it.status == "Completed" }
        else -> appointments
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(16.dp)
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(
                    text = "APPOINTMENT SCHEDULER",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "Track consultations, procedures & reminders",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }

            Button(
                onClick = onAddAppointmentClick,
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Schedule Appt", modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Schedule", fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Filter Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("All", "Scheduled", "Completed").forEach { filter ->
                val isSelected = selectedFilter == filter
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedFilter = filter },
                    label = { Text(filter, fontSize = 12.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Primary,
                        selectedLabelColor = TextPrimary,
                        containerColor = Surface,
                        labelColor = TextSecondary
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (filteredAppointments.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No appointments found for this filter.", color = TextSecondary)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(filteredAppointments) { appt ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Surface, shape = RoundedCornerShape(12.dp))
                            .border(1.dp, CardBorder, shape = RoundedCornerShape(12.dp))
                            .padding(16.dp)
                    ) {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = appt.patientName,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                StatusBadge(status = appt.status)
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Physician: ${appt.doctorName} (${appt.department})",
                                fontSize = 13.sp,
                                color = TextSecondary
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Event, contentDescription = null, tint = Accent, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${appt.date} at ${appt.time} • [${appt.type}]",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Accent
                                )
                            }

                            if (appt.notes.isNotBlank()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Notes: ${appt.notes}",
                                    fontSize = 12.sp,
                                    color = TextSecondary
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                if (appt.status != "Completed") {
                                    TextButton(onClick = { onStatusChange(appt, "Completed") }) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Mark Completed", color = SuccessGreen, fontSize = 12.sp)
                                    }
                                }

                                if (appt.status != "Cancelled") {
                                    TextButton(onClick = { onStatusChange(appt, "Cancelled") }) {
                                        Text("Cancel", color = CriticalRed, fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
