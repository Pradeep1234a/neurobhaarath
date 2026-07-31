package com.neurobharath.patientmanager.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.neurobharath.patientmanager.data.local.entity.CommunicationEntity
import com.neurobharath.patientmanager.data.local.entity.PatientEntity
import com.neurobharath.patientmanager.ui.components.StatusBadge
import com.neurobharath.patientmanager.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunicationScreen(
    communications: List<CommunicationEntity>,
    patients: List<PatientEntity>,
    onSendMessage: (recipient: String, patientId: Int, content: String, channel: String) -> Unit
) {
    var recipientName by remember { mutableStateOf(patients.firstOrNull()?.name ?: "Rajesh Sharma") }
    var selectedPatientId by remember { mutableStateOf(patients.firstOrNull()?.id ?: 1) }
    var messageText by remember { mutableStateOf("") }
    var selectedChannel by remember { mutableStateOf("Direct Message") }

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
                    text = "CLINICAL COMMUNICATION HUB",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "Secure patient messaging, clinical email logs & teleconsultation",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }

            IconButton(
                onClick = { /* Teleconsultation action */ },
                modifier = Modifier
                    .background(Primary, shape = RoundedCornerShape(8.dp))
                    .size(40.dp)
            ) {
                Icon(Icons.Default.VideoCall, contentDescription = "Teleconsultation", tint = TextPrimary)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Message Composer Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Surface, shape = RoundedCornerShape(12.dp))
                .border(1.dp, CardBorder, shape = RoundedCornerShape(12.dp))
                .padding(14.dp)
        ) {
            Column {
                Text("Compose Message / Clinical Note", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Accent)

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = recipientName,
                        onValueChange = { recipientName = it },
                        label = { Text("Recipient Patient", color = TextSecondary) },
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
                        value = selectedChannel,
                        onValueChange = { selectedChannel = it },
                        label = { Text("Channel", color = TextSecondary) },
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
                    value = messageText,
                    onValueChange = { messageText = it },
                    placeholder = { Text("Type clinical note, advice, or reminder...", color = TextSecondary, fontSize = 12.sp) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        focusedBorderColor = Primary,
                        unfocusedBorderColor = CardBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        if (messageText.isNotBlank()) {
                            onSendMessage(recipientName, selectedPatientId, messageText, selectedChannel)
                            messageText = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Dispatch Note", fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "COMMUNICATION HISTORY",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = TextSecondary,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(communications) { comm ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Surface, shape = RoundedCornerShape(12.dp))
                        .border(1.dp, CardBorder, shape = RoundedCornerShape(12.dp))
                        .padding(14.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "From: ${comm.senderName} → To: ${comm.recipientName}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            StatusBadge(status = comm.channelType)
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = comm.content,
                            fontSize = 13.sp,
                            color = TextSecondary
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = comm.timestamp,
                            fontSize = 10.sp,
                            color = Accent,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}
