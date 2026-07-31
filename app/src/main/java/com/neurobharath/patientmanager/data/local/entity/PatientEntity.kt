package com.neurobharath.patientmanager.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patients")
data class PatientEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "patient_id")
    val patientCode: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "age")
    val age: Int,

    @ColumnInfo(name = "gender")
    val gender: String,

    @ColumnInfo(name = "contact")
    val contact: String,

    @ColumnInfo(name = "demographic")
    val demographic: String,

    @ColumnInfo(name = "medical_history")
    val medicalHistory: String,

    @ColumnInfo(name = "treatment_information")
    val treatmentInformation: String
)
