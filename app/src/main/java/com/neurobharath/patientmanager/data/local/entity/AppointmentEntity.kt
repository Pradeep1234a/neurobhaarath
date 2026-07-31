package com.neurobharath.patientmanager.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "appointments",
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
data class AppointmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "patient_id")
    val patientId: Int,

    @ColumnInfo(name = "patient_name")
    val patientName: String,

    @ColumnInfo(name = "doctor_name")
    val doctorName: String,

    @ColumnInfo(name = "department")
    val department: String,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "time")
    val time: String,

    @ColumnInfo(name = "type")
    val type: String, // Consultation, Procedure, Follow-up

    @ColumnInfo(name = "status")
    val status: String, // Scheduled, Completed, Cancelled

    @ColumnInfo(name = "notes")
    val notes: String
)
