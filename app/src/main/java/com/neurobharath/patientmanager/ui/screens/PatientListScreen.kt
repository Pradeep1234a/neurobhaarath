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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neurobharath.patientmanager.data.local.entity.PatientEntity
import com.neurobharath.patientmanager.ui.theme.*

@Composable
fun PatientListScreen(
    patients: List<PatientEntity>,
    onAddPatientClick: () -> Unit,
    onEditPatientClick: (PatientEntity) -> Unit,
    onDeletePatientClick: (PatientEntity) -> Unit
) {
    var selectedPatientDetail by remember { mutableStateOf<PatientEntity?>(null) }

    Box(modifier = Modifier.fillMaxSize().background(Background)) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            // Header Bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "PATIENT RECORDS",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "${patients.size} registered patients in Neuro Bharath database",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }

                Button(
                    onClick = onAddPatientClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Patient", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add Patient", fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            if (patients.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No patient records found. Click 'Add Patient' to create one.", color = TextSecondary)
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(patients) { patient ->
                        PatientCard(
                            patient = patient,
                            onCardClick = { selectedPatientDetail = patient },
                            onEditClick = { onEditPatientClick(patient) },
                            onDeleteClick = { onDeletePatientClick(patient) }
                        )
                    }
                }
            }
        }

        // Detailed View Modal / Drawer
        selectedPatientDetail?.let { patient ->
            PatientDetailModal(
                patient = patient,
                onDismiss = { selectedPatientDetail = null }
            )
        }
    }
}

@Composable
fun PatientCard(
    patient: PatientEntity,
    onCardClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Surface, shape = RoundedCornerShape(12.dp))
            .border(1.dp, CardBorder, shape = RoundedCornerShape(12.dp))
            .clickable { onCardClick() }
            .padding(16.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .background(Accent.copy(alpha = 0.15f), shape = RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = patient.patientCode,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Accent
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = patient.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                Row {
                    IconButton(onClick = onEditClick, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Primary, modifier = Modifier.size(18.dp))
                    }
                    IconButton(onClick = onDeleteClick, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = CriticalRed, modifier = Modifier.size(18.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Demographics: ${patient.age} yrs • ${patient.gender} • ${patient.demographic}",
                fontSize = 12.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Medical History: ${patient.medicalHistory}",
                fontSize = 12.sp,
                color = TextPrimary,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Treatment Plan: ${patient.treatmentInformation}",
                fontSize = 11.sp,
                color = SuccessGreen
            )
        }
    }
}

@Composable
fun PatientDetailModal(
    patient: PatientEntity,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text(patient.name, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text("Patient Code: ${patient.patientCode}", fontSize = 12.sp, color = Accent)
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Demographics: ${patient.age} yrs • ${patient.gender}", fontSize = 13.sp, color = TextSecondary)
                Text("Contact Phone: ${patient.contact}", fontSize = 13.sp, color = TextSecondary)
                Text("Location: ${patient.demographic}", fontSize = 13.sp, color = TextSecondary)
                Divider(color = CardBorder)
                Text("Medical History:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text(patient.medicalHistory, fontSize = 12.sp, color = TextSecondary)
                Divider(color = CardBorder)
                Text("Treatment Information:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SuccessGreen)
                Text(patient.treatmentInformation, fontSize = 12.sp, color = TextPrimary)
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close", color = Primary)
            }
        },
        containerColor = Surface
    )
}
