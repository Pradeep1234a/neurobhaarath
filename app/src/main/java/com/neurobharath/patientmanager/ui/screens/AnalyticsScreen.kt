package com.neurobharath.patientmanager.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neurobharath.patientmanager.data.local.entity.AnalyticsEntity
import com.neurobharath.patientmanager.ui.components.StatusBadge
import com.neurobharath.patientmanager.ui.theme.*

@Composable
fun AnalyticsScreen(
    analyticsList: List<AnalyticsEntity>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Column {
                Text(
                    text = "ANALYTICS & PERFORMANCE REPORTING",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "PRD KPI metrics tracking, patient inflow trends & care compliance",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }
        }

        // PRD Success Goals Progress Cards
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
                            text = "MEASURABLE PRD SUCCESS GOALS",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Accent,
                            letterSpacing = 1.sp
                        )
                        Icon(Icons.Default.TrendingUp, contentDescription = null, tint = SuccessGreen)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    MetricProgressRow("Patient Record Efficiency (+25% Goal)", 0.85f, "28.5% Achieved")
                    Spacer(modifier = Modifier.height(10.dp))
                    MetricProgressRow("Appointment Conflict Reduction (-50% Goal)", 0.92f, "52.0% Reduced")
                    Spacer(modifier = Modifier.height(10.dp))
                    MetricProgressRow("User Action & Engagement (+30% Goal)", 0.88f, "34.2% Growth")
                }
            }
        }

        // Analytical Metrics Breakdown
        item {
            Text(
                text = "LOGGED HEALTHCARE METRICS",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextSecondary,
                letterSpacing = 1.sp
            )
        }

        items(analyticsList) { item ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Surface, shape = RoundedCornerShape(12.dp))
                    .border(1.dp, CardBorder, shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = item.metric,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Category: ${item.category} • Updated: ${item.timestamp}",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .background(Primary.copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "${item.value}%",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MetricProgressRow(
    title: String,
    progress: Float,
    statusText: String
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Text(text = statusText, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SuccessGreen)
        }

        Spacer(modifier = Modifier.height(6.dp))

        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = Primary,
            trackColor = SurfaceVariant
        )
    }
}
