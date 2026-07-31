package com.neurobharath.patientmanager.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.neurobharath.patientmanager.data.local.entity.PatientEntity
import com.neurobharath.patientmanager.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLabTestDialog(
    patientsList: List<PatientEntity>,
    onDismiss: () -> Unit,
    onSave: (patientId: Int, patientName: String, testName: String, category: String, value: String, unit: String, range: String, status: String, interpretation: String, date: String) -> Unit
) {
    var selectedPatientId by remember { mutableStateOf(patientsList.firstOrNull()?.id ?: 1) }
    var selectedPatientName by remember { mutableStateOf(patientsList.firstOrNull()?.name ?: "Rajesh Sharma") }
    var testName by remember { mutableStateOf("Brain MRI with Contrast") }
    var category by remember { mutableStateOf("Imaging") }
    var resultValue by remember { mutableStateOf("Normal") }
    var unit by remember { mutableStateOf("Qualitative") }
    var referenceRange by remember { mutableStateOf("No acute lesions") }
    var status by remember { mutableStateOf("Normal") }
    var interpretation by remember { mutableStateOf("No acute abnormality detected.") }
    var date by remember { mutableStateOf("2026-07-31") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Surface,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, CardBorder, shape = RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Record Lab Test / Report",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Accent
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = selectedPatientName,
                    onValueChange = { selectedPatientName = it },
                    label = { Text("Patient Name", color = TextSecondary) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        focusedBorderColor = Primary,
                        unfocusedBorderColor = CardBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = testName,
                    onValueChange = { testName = it },
                    label = { Text("Test Name (e.g. EEG, Serum Level)", color = TextSecondary) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        focusedBorderColor = Primary,
                        unfocusedBorderColor = CardBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = category,
                        onValueChange = { category = it },
                        label = { Text("Category", color = TextSecondary) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = CardBorder
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = status,
                        onValueChange = { status = it },
                        label = { Text("Status (Normal/Critical)", color = TextSecondary) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = CardBorder
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = resultValue,
                    onValueChange = { resultValue = it },
                    label = { Text("Result Value", color = TextSecondary) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        focusedBorderColor = Primary,
                        unfocusedBorderColor = CardBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = interpretation,
                    onValueChange = { interpretation = it },
                    label = { Text("Clinical Interpretation", color = TextSecondary) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        focusedBorderColor = Primary,
                        unfocusedBorderColor = CardBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel", color = TextSecondary)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            onSave(
                                selectedPatientId,
                                selectedPatientName,
                                testName,
                                category,
                                resultValue,
                                unit,
                                referenceRange,
                                status,
                                interpretation,
                                date
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Primary)
                    ) {
                        Text("Save Lab Report", color = TextPrimary)
                    }
                }
            }
        }
    }
}
