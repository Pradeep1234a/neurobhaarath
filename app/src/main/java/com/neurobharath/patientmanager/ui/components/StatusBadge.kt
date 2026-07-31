package com.neurobharath.patientmanager.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neurobharath.patientmanager.ui.theme.*

@Composable
fun StatusBadge(
    status: String,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor) = when (status.lowercase()) {
        "normal", "active", "completed", "scheduled" -> Pair(SuccessGreen.copy(alpha = 0.2f), SuccessGreen)
        "critical", "discontinued", "cancelled" -> Pair(CriticalRed.copy(alpha = 0.2f), CriticalRed)
        "refill needed", "pending", "rescheduled" -> Pair(PendingAmber.copy(alpha = 0.2f), PendingAmber)
        else -> Pair(Secondary.copy(alpha = 0.2f), Secondary)
    }

    Text(
        text = status.uppercase(),
        color = textColor,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .background(bgColor, shape = RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}
