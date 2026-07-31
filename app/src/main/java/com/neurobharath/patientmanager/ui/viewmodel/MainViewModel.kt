package com.neurobharath.patientmanager.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.neurobharath.patientmanager.data.local.AppDatabase
import com.neurobharath.patientmanager.data.local.entity.*
import com.neurobharath.patientmanager.data.repository.PatientRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class NavigationScreen(val title: String) {
    SPLASH("Splash"),
    AUTH("Authentication"),
    DASHBOARD("Overview Dashboard"),
    PATIENTS("Patient Records"),
    APPOINTMENTS("Appointments"),
    MEDICATIONS("Medications"),
    LAB_TESTS("Lab Test Results"),
    COMMUNICATION("Communication"),
    ANALYTICS("Analytics & Reports")
}

data class UserSession(
    val isAuthenticated: Boolean = true,
    val role: String = "Healthcare Professional", // Healthcare Professional, Administrator, Patient
    val username: String = "Dr. K. S. Bharath"
)

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PatientRepository

    // Screen State
    private val _currentScreen = MutableStateFlow(NavigationScreen.SPLASH)
    val currentScreen: StateFlow<NavigationScreen> = _currentScreen.asStateFlow()

    // Auth State
    private val _userSession = MutableStateFlow(UserSession())
    val userSession: StateFlow<UserSession> = _userSession.asStateFlow()

    // Search Query
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Dialog Control States
    private val _showAddPatientDialog = MutableStateFlow(false)
    val showAddPatientDialog: StateFlow<Boolean> = _showAddPatientDialog.asStateFlow()

    private val _showAddAppointmentDialog = MutableStateFlow(false)
    val showAddAppointmentDialog: StateFlow<Boolean> = _showAddAppointmentDialog.asStateFlow()

    private val _showAddMedicationDialog = MutableStateFlow(false)
    val showAddMedicationDialog: StateFlow<Boolean> = _showAddMedicationDialog.asStateFlow()

    private val _showAddLabTestDialog = MutableStateFlow(false)
    val showAddLabTestDialog: StateFlow<Boolean> = _showAddLabTestDialog.asStateFlow()

    private val _selectedPatient = MutableStateFlow<PatientEntity?>(null)
    val selectedPatient: StateFlow<PatientEntity?> = _selectedPatient.asStateFlow()

    // Flow Queries from Room
    val patients: StateFlow<List<PatientEntity>>
    val appointments: StateFlow<List<AppointmentEntity>>
    val medications: StateFlow<List<MedicationEntity>>
    val labTests: StateFlow<List<LabTestEntity>>
    val communications: StateFlow<List<CommunicationEntity>>
    val analytics: StateFlow<List<AnalyticsEntity>>

    init {
        val database = AppDatabase.getDatabase(application, viewModelScope)
        repository = PatientRepository(
            database.patientDao(),
            database.appointmentDao(),
            database.medicationDao(),
            database.labTestDao(),
            database.communicationDao(),
            database.analyticsDao()
        )

        patients = _searchQuery
            .flatMapLatest { query ->
                if (query.isBlank()) repository.allPatients
                else repository.searchPatients(query)
            }
            .catch { emit(emptyList()) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        appointments = repository.allAppointments
            .catch { emit(emptyList()) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        medications = repository.allMedications
            .catch { emit(emptyList()) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        labTests = repository.allLabTests
            .catch { emit(emptyList()) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        communications = repository.allCommunications
            .catch { emit(emptyList()) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        analytics = repository.allAnalytics
            .catch { emit(emptyList()) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }

    fun navigateTo(screen: NavigationScreen) {
        _currentScreen.value = screen
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun login(username: String, role: String) {
        _userSession.value = UserSession(isAuthenticated = true, role = role, username = username)
        _currentScreen.value = NavigationScreen.DASHBOARD
    }

    fun logout() {
        _userSession.value = UserSession(isAuthenticated = false)
        _currentScreen.value = NavigationScreen.AUTH
    }

    // Dialog Triggers
    fun openAddPatientDialog(patient: PatientEntity? = null) {
        _selectedPatient.value = patient
        _showAddPatientDialog.value = true
    }

    fun closeAddPatientDialog() {
        _showAddPatientDialog.value = false
        _selectedPatient.value = null
    }

    fun openAddAppointmentDialog() {
        _showAddAppointmentDialog.value = true
    }

    fun closeAddAppointmentDialog() {
        _showAddAppointmentDialog.value = false
    }

    fun openAddMedicationDialog() {
        _showAddMedicationDialog.value = true
    }

    fun closeAddMedicationDialog() {
        _showAddMedicationDialog.value = false
    }

    fun openAddLabTestDialog() {
        _showAddLabTestDialog.value = true
    }

    fun closeAddLabTestDialog() {
        _showAddLabTestDialog.value = false
    }

    // Patient Actions
    fun savePatient(
        id: Int = 0,
        patientCode: String,
        name: String,
        age: Int,
        gender: String,
        contact: String,
        demographic: String,
        medicalHistory: String,
        treatmentInfo: String
    ) {
        viewModelScope.launch {
            val patient = PatientEntity(
                id = id,
                patientCode = patientCode.ifBlank { "NB-${(1000..9999).random()}" },
                name = name,
                age = age,
                gender = gender,
                contact = contact,
                demographic = demographic,
                medicalHistory = medicalHistory,
                treatmentInformation = treatmentInfo
            )
            if (id == 0) {
                repository.addPatient(patient)
            } else {
                repository.updatePatient(patient)
            }
            closeAddPatientDialog()
        }
    }

    fun deletePatient(patient: PatientEntity) {
        viewModelScope.launch {
            repository.deletePatient(patient)
        }
    }

    // Appointment Actions
    fun saveAppointment(
        patientId: Int,
        patientName: String,
        doctorName: String,
        department: String,
        date: String,
        time: String,
        type: String,
        notes: String
    ) {
        viewModelScope.launch {
            val appointment = AppointmentEntity(
                patientId = patientId,
                patientName = patientName,
                doctorName = doctorName,
                department = department,
                date = date,
                time = time,
                type = type,
                status = "Scheduled",
                notes = notes
            )
            repository.addAppointment(appointment)
            closeAddAppointmentDialog()
        }
    }

    fun updateAppointmentStatus(appointment: AppointmentEntity, newStatus: String) {
        viewModelScope.launch {
            repository.updateAppointment(appointment.copy(status = newStatus))
        }
    }

    // Medication Actions
    fun saveMedication(
        patientId: Int,
        patientName: String,
        name: String,
        dosage: String,
        frequency: String,
        quantity: Int,
        prescriptionStatus: String
    ) {
        viewModelScope.launch {
            val medication = MedicationEntity(
                patientId = patientId,
                patientName = patientName,
                name = name,
                quantity = quantity,
                prescriptionStatus = prescriptionStatus,
                dosage = dosage,
                frequency = frequency
            )
            repository.addMedication(medication)
            closeAddMedicationDialog()
        }
    }

    fun deleteMedication(medication: MedicationEntity) {
        viewModelScope.launch {
            repository.deleteMedication(medication)
        }
    }

    // Lab Test Actions
    fun saveLabTest(
        patientId: Int,
        patientName: String,
        testName: String,
        category: String,
        resultValue: String,
        unit: String,
        referenceRange: String,
        status: String,
        interpretation: String,
        date: String
    ) {
        viewModelScope.launch {
            val labTest = LabTestEntity(
                patientId = patientId,
                patientName = patientName,
                testName = testName,
                category = category,
                resultValue = resultValue,
                unit = unit,
                referenceRange = referenceRange,
                status = status,
                interpretation = interpretation,
                date = date
            )
            repository.addLabTest(labTest)
            closeAddLabTestDialog()
        }
    }

    // Communication Actions
    fun sendMessage(
        recipientName: String,
        patientId: Int,
        content: String,
        channelType: String
    ) {
        viewModelScope.launch {
            val comm = CommunicationEntity(
                senderId = 1,
                senderName = _userSession.value.username,
                recipientId = 2,
                recipientName = recipientName,
                patientId = patientId,
                content = content,
                channelType = channelType,
                timestamp = "2026-07-31 07:15 PM"
            )
            repository.addCommunication(comm)
        }
    }
}
