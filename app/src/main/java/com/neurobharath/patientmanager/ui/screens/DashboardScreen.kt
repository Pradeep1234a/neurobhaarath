package com.neurobharath.patientmanager.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neurobharath.patientmanager.data.local.entity.*
import com.neurobharath.patientmanager.ui.components.StatCard
import com.neurobharath.patientmanager.ui.components.StatusBadge
import com.neurobharath.patientmanager.ui.theme.*
import com.neurobharath.patientmanager.ui.viewmodel.NavigationScreen

@Composable
fun DashboardScreen(
    patients: List<PatientEntity>,
    appointments: List<AppointmentEntity>,
    medications: List<MedicationEntity>,
    communications: List<CommunicationEntity>,
    onNavigate: (NavigationScreen) -> Unit,
    onAddPatientClick: () -> Unit,
    onAddAppointmentClick: () -> Unit,
    onAddMedicationClick: () -> Unit,
    onAddLabTestClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Hospital Welcome Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Surface, shape = RoundedCornerShape(14.dp))
                    .border(1.dp, CardBorder, shape = RoundedCornerShape(14.dp))
                    .padding(18.dp)
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "NEURO BHARATH HOSPITAL",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Accent,
                            letterSpacing = 1.sp
                        )
                        StatusBadge(status = "SYSTEM ACTIVE")
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Cross-Device Patient Care Portal",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )

                    Text(
                        text = "Streamlining patient care, appointment reminders, diagnostic labs, and clinical records.",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }
            }
        }

        // 2. Metrics Grid (PRD 4 Key Counters)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(
                        title = "Total Patients",
                        value = "${patients.size}",
                        subtitle = "Active records",
                        icon = Icons.Default.People,
                        accentColor = Primary,
                        modifier = Modifier.weight(1f)
                    )

                    StatCard(
                        title = "Appointments",
                        value = "${appointments.size}",
                        subtitle = "Scheduled today",
                        icon = Icons.Default.Event,
                        accentColor = Accent,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(
                        title = "Active Meds",
                        value = "${medications.size}",
                        subtitle = "Tracked dosages",
                        icon = Icons.Default.Medication,
                        accentColor = SuccessGreen,
                        modifier = Modifier.weight(1f)
                    )

                    StatCard(
                        title = "Clinical Notes",
                        value = "${communications.size}",
                        subtitle = "Recent logs",
                        icon = Icons.Default.Chat,
                        accentColor = PendingAmber,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // 3. Quick Action Shortcuts
        item {
            Column {
                Text(
                    text = "QUICK ACTIONS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    QuickActionButton("New Patient", Icons.Default.PersonAdd, Primary, onAddPatientClick, Modifier.weight(1f))
                    QuickActionButton("Book Appt", Icons.Default.AddTask, Accent, onAddAppointmentClick, Modifier.weight(1f))
                    QuickActionButton("Add Med", Icons.Default.MedicalServices, SuccessGreen, onAddMedicationClick, Modifier.weight(1f))
                    QuickActionButton("Lab Test", Icons.Default.Biotech, PendingAmber, onAddLabTestClick, Modifier.weight(1f))
                }
            }
        }

        // 4. Upcoming Appointments Summary
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "TODAY'S APPOINTMENTS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "View All",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Accent,
                    modifier = Modifier.clickable { onNavigate(NavigationScreen.APPOINTMENTS) }
                )
            }
        }

        items(appointments.take(3)) { appt ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Surface, shape = RoundedCornerShape(10.dp))
                    .border(1.dp, CardBorder, shape = RoundedCornerShape(10.dp))
                    .padding(14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(text = appt.patientName, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Text(text = "${appt.doctorName} • ${appt.department}", fontSize = 12.sp, color = TextSecondary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "${appt.date} at ${appt.time}", fontSize = 11.sp, color = Accent, fontWeight = FontWeight.Medium)
                    }
                    StatusBadge(status = appt.status)
                }
            }
        }

        // 5. Recent Activity Feed
        item {
            Text(
                text = "RECENT CLINICAL LOGS",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextSecondary,
                letterSpacing = 1.sp
            )
        }

        items(communications.take(2)) { comm ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Surface, shape = RoundedCornerShape(10.dp))
                    .border(1.dp, CardBorder, shape = RoundedCornerShape(10.dp))
                    .padding(12.dp)
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = comm.senderName, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Text(text = comm.timestamp, fontSize = 10.sp, color = TextSecondary)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = comm.content, fontSize = 12.sp, color = TextSecondary)
                }
            }
        }
    }
}

@Composable
fun QuickActionButton(
    label: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Surface),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 4.dp),
        modifier = modifier.border(1.dp, color.copy(alpha = 0.5f), shape = RoundedCornerShape(10.dp))
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = icon, contentDescription = label, tint = color, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = label, fontSize = 10.sp, color = TextPrimary, fontWeight = FontWeight.SemiBold)
        }
    }
}
