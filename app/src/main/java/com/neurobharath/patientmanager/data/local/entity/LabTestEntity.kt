package com.neurobharath.patientmanager.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "lab_tests",
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
data class LabTestEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "patient_id")
    val patientId: Int,

    @ColumnInfo(name = "patient_name")
    val patientName: String,

    @ColumnInfo(name = "test_name")
    val testName: String,

    @ColumnInfo(name = "category")
    val category: String, // Neurology, Blood Work, Imaging, EEG

    @ColumnInfo(name = "result_value")
    val resultValue: String,

    @ColumnInfo(name = "unit")
    val unit: String,

    @ColumnInfo(name = "reference_range")
    val referenceRange: String,

    @ColumnInfo(name = "status")
    val status: String, // Normal, Critical, Pending

    @ColumnInfo(name = "interpretation")
    val interpretation: String,

    @ColumnInfo(name = "date")
    val date: String
)
