package com.neurobharath.patientmanager.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "communication",
    foreignKeys = [
        ForeignKey(
            entity = PatientEntity::class,
            parentColumns = ["id"],
            childColumns = ["patient_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("patient_id")]
)
data class CommunicationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "sender_id")
    val senderId: Int,

    @ColumnInfo(name = "sender_name")
    val senderName: String,

    @ColumnInfo(name = "recipient_id")
    val recipientId: Int,

    @ColumnInfo(name = "recipient_name")
    val recipientName: String,

    @ColumnInfo(name = "patient_id")
    val patientId: Int,

    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "channel_type")
    val channelType: String, // Direct Message, Email/Clinical Note, Teleconsultation

    @ColumnInfo(name = "timestamp")
    val timestamp: String
)
