package com.neurobharath.patientmanager.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Biotech
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neurobharath.patientmanager.data.local.entity.LabTestEntity
import com.neurobharath.patientmanager.ui.components.StatusBadge
import com.neurobharath.patientmanager.ui.theme.*

@Composable
fun LabTestScreen(
    labTests: List<LabTestEntity>,
    onAddLabTestClick: () -> Unit
) {
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
                    text = "LAB TEST RESULTS & REPORTS",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "Diagnostics, lab parameters, reference ranges & interpretations",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }

            Button(
                onClick = onAddLabTestClick,
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Lab Test", modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Add Report", fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (labTests.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No lab test records found.", color = TextSecondary)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(labTests) { test ->
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
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Biotech, contentDescription = null, tint = Accent, modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = test.testName,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                }
                                StatusBadge(status = test.status)
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Patient: ${test.patientName} • Category: ${test.category} • Date: ${test.date}",
                                fontSize = 12.sp,
                                color = TextSecondary
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Column {
                                    Text("RESULT VALUE", fontSize = 10.sp, color = TextSecondary, fontWeight = FontWeight.Bold)
                                    Text("${test.resultValue} ${test.unit}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Accent)
                                }

                                Column {
                                    Text("REF RANGE", fontSize = 10.sp, color = TextSecondary, fontWeight = FontWeight.Bold)
                                    Text(test.referenceRange, fontSize = 13.sp, color = TextPrimary)
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Interpretation: ${test.interpretation}",
                                fontSize = 12.sp,
                                color = TextPrimary,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}
