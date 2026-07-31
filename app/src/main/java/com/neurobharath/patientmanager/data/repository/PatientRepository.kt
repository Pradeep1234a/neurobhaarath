package com.neurobharath.patientmanager.data.repository

import com.neurobharath.patientmanager.data.local.entity.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PatientRepository {

    private val _patients = MutableStateFlow<List<PatientEntity>>(emptyList())
    val allPatients: Flow<List<PatientEntity>> = _patients.asStateFlow()

    private val _appointments = MutableStateFlow<List<AppointmentEntity>>(emptyList())
    val allAppointments: Flow<List<AppointmentEntity>> = _appointments.asStateFlow()

    private val _medications = MutableStateFlow<List<MedicationEntity>>(emptyList())
    val allMedications: Flow<List<MedicationEntity>> = _medications.asStateFlow()

    private val _labTests = MutableStateFlow<List<LabTestEntity>>(emptyList())
    val allLabTests: Flow<List<LabTestEntity>> = _labTests.asStateFlow()

    private val _communications = MutableStateFlow<List<CommunicationEntity>>(emptyList())
    val allCommunications: Flow<List<CommunicationEntity>> = _communications.asStateFlow()

    private val _analytics = MutableStateFlow<List<AnalyticsEntity>>(emptyList())
    val allAnalytics: Flow<List<AnalyticsEntity>> = _analytics.asStateFlow()

    init {
        seedInitialData()
    }

    private fun seedInitialData() {
        val p1 = PatientEntity(
            id = 1,
            patientCode = "NB-1001",
            name = "Rajesh Sharma",
            age = 54,
            gender = "Male",
            contact = "+91 98765 43210",
            demographic = "Shivamogga, Karnataka (A+ Positive)",
            medicalHistory = "Type 2 Diabetes, Mild Hypertension, Chronic Migraines",
            treatmentInformation = "Levetiracetam 500mg, Regular Neuro checkups every 3 months"
        )
        val p2 = PatientEntity(
            id = 2,
            patientCode = "NB-1002",
            name = "Ananya Iyer",
            age = 32,
            gender = "Female",
            contact = "+91 91234 56789",
            demographic = "Chennai, Tamil Nadu (O+ Positive)",
            medicalHistory = "Epilepsy (Partial Seizures), Vitamin D Deficiency",
            treatmentInformation = "Sodium Valproate 300mg BD, EEG Monitoring scheduled"
        )
        val p3 = PatientEntity(
            id = 3,
            patientCode = "NB-1003",
            name = "Vikram Patil",
            age = 67,
            gender = "Male",
            contact = "+91 99887 76655",
            demographic = "Hubballi, Karnataka (B+ Positive)",
            medicalHistory = "Early-stage Parkinson's Disease, Tremors",
            treatmentInformation = "Levodopa/Carbidopa 100/25mg, Physical therapy twice weekly"
        )
        val p4 = PatientEntity(
            id = 4,
            patientCode = "NB-1004",
            name = "Priya Deshmukh",
            age = 29,
            gender = "Female",
            contact = "+91 98450 11223",
            demographic = "Pune, Maharashtra (AB+ Positive)",
            medicalHistory = "Multiple Sclerosis (Relapsing-Remitting)",
            treatmentInformation = "Interferon beta-1a, Bi-monthly MRI scans"
        )
        _patients.value = listOf(p1, p2, p3, p4)

        _appointments.value = listOf(
            AppointmentEntity(
                id = 1,
                patientId = 1,
                patientName = "Rajesh Sharma",
                doctorName = "Dr. K. S. Bharath (Senior Neurologist)",
                department = "Neurology OPD",
                date = "2026-08-02",
                time = "10:30 AM",
                type = "Consultation",
                status = "Scheduled",
                notes = "Quarterly progress assessment & EEG review."
            ),
            AppointmentEntity(
                id = 2,
                patientId = 2,
                patientName = "Ananya Iyer",
                doctorName = "Dr. Meera Nambiar (Epileptologist)",
                department = "Neurodiagnostics",
                date = "2026-08-03",
                time = "02:00 PM",
                type = "Procedure",
                status = "Scheduled",
                notes = "24-Hour Video EEG Monitoring session."
            ),
            AppointmentEntity(
                id = 3,
                patientId = 3,
                patientName = "Vikram Patil",
                doctorName = "Dr. K. S. Bharath",
                department = "Movement Disorders Clinic",
                date = "2026-07-28",
                time = "11:00 AM",
                type = "Follow-up",
                status = "Completed",
                notes = "Tremor reduction noted. Dosage maintained."
            )
        )

        _medications.value = listOf(
            MedicationEntity(
                id = 1,
                patientId = 1,
                patientName = "Rajesh Sharma",
                name = "Keppra (Levetiracetam)",
                quantity = 45,
                prescriptionStatus = "Active",
                dosage = "500 mg",
                frequency = "Twice Daily (Morning / Night)"
            ),
            MedicationEntity(
                id = 2,
                patientId = 2,
                patientName = "Ananya Iyer",
                name = "Epilim (Sodium Valproate)",
                quantity = 10,
                prescriptionStatus = "Refill Needed",
                dosage = "300 mg",
                frequency = "Twice Daily"
            ),
            MedicationEntity(
                id = 3,
                patientId = 3,
                patientName = "Vikram Patil",
                name = "Sinemet (Levodopa/Carbidopa)",
                quantity = 60,
                prescriptionStatus = "Active",
                dosage = "100/25 mg",
                frequency = "Three times daily after meals"
            )
        )

        _labTests.value = listOf(
            LabTestEntity(
                id = 1,
                patientId = 1,
                patientName = "Rajesh Sharma",
                testName = "Brain MRI with Contrast",
                category = "Imaging",
                resultValue = "Normal Ventricles, No Lesions",
                unit = "Qualitative",
                referenceRange = "No acute ischemia",
                status = "Normal",
                interpretation = "Brain tissue intact without acute changes.",
                date = "2026-07-25"
            ),
            LabTestEntity(
                id = 2,
                patientId = 2,
                patientName = "Ananya Iyer",
                testName = "Serum Valproic Acid Level",
                category = "Blood Work",
                resultValue = "42.5",
                unit = "µg/mL",
                referenceRange = "50.0 - 100.0",
                status = "Critical",
                interpretation = "Sub-therapeutic concentration. Dosage adjustment required.",
                date = "2026-07-29"
            )
        )

        _communications.value = listOf(
            CommunicationEntity(
                id = 1,
                senderId = 101,
                senderName = "Dr. K. S. Bharath",
                recipientId = 1,
                recipientName = "Rajesh Sharma",
                patientId = 1,
                content = "Please ensure blood sugar logs are brought to your upcoming OPD appointment on Aug 2.",
                channelType = "Direct Message",
                timestamp = "2026-07-31 09:15 AM"
            )
        )

        _analytics.value = listOf(
            AnalyticsEntity(
                id = 1,
                patientId = 1,
                metric = "Record Management Efficiency",
                value = 28.5,
                category = "KPI Goal (+25% achieved)",
                timestamp = "2026-07-31"
            ),
            AnalyticsEntity(
                id = 2,
                patientId = 2,
                metric = "Scheduling Error Reduction",
                value = 52.0,
                category = "KPI Goal (-50% achieved)",
                timestamp = "2026-07-31"
            ),
            AnalyticsEntity(
                id = 3,
                patientId = 3,
                metric = "Patient Action & Engagement",
                value = 34.2,
                category = "KPI Goal (+30% achieved)",
                timestamp = "2026-07-31"
            )
        )
    }

    fun searchPatients(query: String): Flow<List<PatientEntity>> {
        val current = _patients.value
        val filtered = current.filter {
            it.name.contains(query, ignoreCase = true) ||
            it.patientCode.contains(query, ignoreCase = true) ||
            it.demographic.contains(query, ignoreCase = true)
        }
        return MutableStateFlow(filtered)
    }

    fun addPatient(patient: PatientEntity): Long {
        val current = _patients.value.toMutableList()
        val newId = (current.maxOfOrNull { it.id } ?: 0) + 1
        val newPatient = patient.copy(id = newId)
        current.add(0, newPatient)
        _patients.value = current
        return newId.toLong()
    }

    fun updatePatient(patient: PatientEntity) {
        val current = _patients.value.toMutableList()
        val index = current.indexOfFirst { it.id == patient.id }
        if (index != -1) {
            current[index] = patient
            _patients.value = current
        }
    }

    fun deletePatient(patient: PatientEntity) {
        val current = _patients.value.toMutableList()
        current.removeAll { it.id == patient.id }
        _patients.value = current
    }

    fun addAppointment(appointment: AppointmentEntity): Long {
        val current = _appointments.value.toMutableList()
        val newId = (current.maxOfOrNull { it.id } ?: 0) + 1
        val newAppt = appointment.copy(id = newId)
        current.add(0, newAppt)
        _appointments.value = current
        return newId.toLong()
    }

    fun updateAppointment(appointment: AppointmentEntity) {
        val current = _appointments.value.toMutableList()
        val index = current.indexOfFirst { it.id == appointment.id }
        if (index != -1) {
            current[index] = appointment
            _appointments.value = current
        }
    }

    fun addMedication(medication: MedicationEntity): Long {
        val current = _medications.value.toMutableList()
        val newId = (current.maxOfOrNull { it.id } ?: 0) + 1
        val newMed = medication.copy(id = newId)
        current.add(0, newMed)
        _medications.value = current
        return newId.toLong()
    }

    fun deleteMedication(medication: MedicationEntity) {
        val current = _medications.value.toMutableList()
        current.removeAll { it.id == medication.id }
        _medications.value = current
    }

    fun addLabTest(labTest: LabTestEntity): Long {
        val current = _labTests.value.toMutableList()
        val newId = (current.maxOfOrNull { it.id } ?: 0) + 1
        val newTest = labTest.copy(id = newId)
        current.add(0, newTest)
        _labTests.value = current
        return newId.toLong()
    }

    fun addCommunication(communication: CommunicationEntity): Long {
        val current = _communications.value.toMutableList()
        val newId = (current.maxOfOrNull { it.id } ?: 0) + 1
        val newComm = communication.copy(id = newId)
        current.add(0, newComm)
        _communications.value = current
        return newId.toLong()
    }
}
