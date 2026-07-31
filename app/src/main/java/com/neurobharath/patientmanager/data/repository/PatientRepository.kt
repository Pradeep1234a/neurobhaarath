package com.neurobharath.patientmanager.data.repository

import com.neurobharath.patientmanager.data.local.dao.*
import com.neurobharath.patientmanager.data.local.entity.*
import kotlinx.coroutines.flow.Flow

class PatientRepository(
    private val patientDao: PatientDao,
    private val appointmentDao: AppointmentDao,
    private val medicationDao: MedicationDao,
    private val labTestDao: LabTestDao,
    private val communicationDao: CommunicationDao,
    private val analyticsDao: AnalyticsDao
) {
    // Patients API (/patient/create, /patient/read, /patient/update, /patient/delete)
    val allPatients: Flow<List<PatientEntity>> = patientDao.getAllPatients()

    fun searchPatients(query: String): Flow<List<PatientEntity>> = patientDao.searchPatients(query)

    suspend fun getPatientById(id: Int): PatientEntity? = patientDao.getPatientById(id)

    suspend fun addPatient(patient: PatientEntity): Long = patientDao.insertPatient(patient)

    suspend fun updatePatient(patient: PatientEntity) = patientDao.updatePatient(patient)

    suspend fun deletePatient(patient: PatientEntity) = patientDao.deletePatient(patient)

    // Appointments API (/appointment/create, /appointment/get, /appointment/update)
    val allAppointments: Flow<List<AppointmentEntity>> = appointmentDao.getAllAppointments()

    suspend fun addAppointment(appointment: AppointmentEntity): Long = appointmentDao.insertAppointment(appointment)

    suspend fun updateAppointment(appointment: AppointmentEntity) = appointmentDao.updateAppointment(appointment)

    suspend fun deleteAppointment(appointment: AppointmentEntity) = appointmentDao.deleteAppointment(appointment)

    // Medications API (/medication/create, /medication/read, /medication/update, /medication/delete)
    val allMedications: Flow<List<MedicationEntity>> = medicationDao.getAllMedications()

    suspend fun addMedication(medication: MedicationEntity): Long = medicationDao.insertMedication(medication)

    suspend fun updateMedication(medication: MedicationEntity) = medicationDao.updateMedication(medication)

    suspend fun deleteMedication(medication: MedicationEntity) = medicationDao.deleteMedication(medication)

    // Lab Tests API
    val allLabTests: Flow<List<LabTestEntity>> = labTestDao.getAllLabTests()

    suspend fun addLabTest(labTest: LabTestEntity): Long = labTestDao.insertLabTest(labTest)

    suspend fun updateLabTest(labTest: LabTestEntity) = labTestDao.updateLabTest(labTest)

    suspend fun deleteLabTest(labTest: LabTestEntity) = labTestDao.deleteLabTest(labTest)

    // Communication API (/communication/create, /communication/get, /communication/update)
    val allCommunications: Flow<List<CommunicationEntity>> = communicationDao.getAllCommunications()

    suspend fun addCommunication(communication: CommunicationEntity): Long = communicationDao.insertCommunication(communication)

    // Analytics API (/analytics/create, /analytics/get, /analytics/update)
    val allAnalytics: Flow<List<AnalyticsEntity>> = analyticsDao.getAllAnalytics()

    suspend fun addAnalytics(analytics: AnalyticsEntity): Long = analyticsDao.insertAnalytics(analytics)
}
