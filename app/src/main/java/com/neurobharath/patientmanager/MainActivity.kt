package com.neurobharath.patientmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.neurobharath.patientmanager.ui.components.*
import com.neurobharath.patientmanager.ui.screens.*
import com.neurobharath.patientmanager.ui.theme.NeuroBharathTheme
import com.neurobharath.patientmanager.ui.viewmodel.MainViewModel
import com.neurobharath.patientmanager.ui.viewmodel.NavigationScreen

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NeuroBharathTheme {
                NeuroBharathMainApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun NeuroBharathMainApp(viewModel: MainViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val userSession by viewModel.userSession.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    val patients by viewModel.patients.collectAsState()
    val appointments by viewModel.appointments.collectAsState()
    val medications by viewModel.medications.collectAsState()
    val labTests by viewModel.labTests.collectAsState()
    val communications by viewModel.communications.collectAsState()
    val analytics by viewModel.analytics.collectAsState()

    val showAddPatientDialog by viewModel.showAddPatientDialog.collectAsState()
    val showAddAppointmentDialog by viewModel.showAddAppointmentDialog.collectAsState()
    val showAddMedicationDialog by viewModel.showAddMedicationDialog.collectAsState()
    val showAddLabTestDialog by viewModel.showAddLabTestDialog.collectAsState()
    val selectedPatient by viewModel.selectedPatient.collectAsState()

    if (!userSession.isAuthenticated) {
        AuthScreen(
            onLoginSuccess = { username, role -> viewModel.login(username, role) }
        )
    } else {
        Scaffold(
            topBar = {
                TopHeaderBar(
                    currentScreen = currentScreen,
                    searchQuery = searchQuery,
                    onSearchQueryChange = { viewModel.updateSearchQuery(it) },
                    userName = userSession.username,
                    userRole = userSession.role,
                    onNavigate = { viewModel.navigateTo(it) },
                    onLogout = { viewModel.logout() }
                )
            },
            bottomBar = {
                BottomNavBar(
                    currentScreen = currentScreen,
                    onNavigate = { viewModel.navigateTo(it) }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentScreen) {
                    NavigationScreen.AUTH -> AuthScreen(onLoginSuccess = { username, role -> viewModel.login(username, role) })
                    NavigationScreen.DASHBOARD -> DashboardScreen(
                        patients = patients,
                        appointments = appointments,
                        medications = medications,
                        communications = communications,
                        onNavigate = { viewModel.navigateTo(it) },
                        onAddPatientClick = { viewModel.openAddPatientDialog() },
                        onAddAppointmentClick = { viewModel.openAddAppointmentDialog() },
                        onAddMedicationClick = { viewModel.openAddMedicationDialog() },
                        onAddLabTestClick = { viewModel.openAddLabTestDialog() }
                    )
                    NavigationScreen.PATIENTS -> PatientListScreen(
                        patients = patients,
                        onAddPatientClick = { viewModel.openAddPatientDialog() },
                        onEditPatientClick = { viewModel.openAddPatientDialog(it) },
                        onDeletePatientClick = { viewModel.deletePatient(it) }
                    )
                    NavigationScreen.APPOINTMENTS -> AppointmentScreen(
                        appointments = appointments,
                        onAddAppointmentClick = { viewModel.openAddAppointmentDialog() },
                        onStatusChange = { appt, status -> viewModel.updateAppointmentStatus(appt, status) }
                    )
                    NavigationScreen.MEDICATIONS -> MedicationScreen(
                        medications = medications,
                        onAddMedicationClick = { viewModel.openAddMedicationDialog() },
                        onDeleteMedicationClick = { viewModel.deleteMedication(it) }
                    )
                    NavigationScreen.LAB_TESTS -> LabTestScreen(
                        labTests = labTests,
                        onAddLabTestClick = { viewModel.openAddLabTestDialog() }
                    )
                    NavigationScreen.COMMUNICATION -> CommunicationScreen(
                        communications = communications,
                        patients = patients,
                        onSendMessage = { recipient, patientId, content, channel ->
                            viewModel.sendMessage(recipient, patientId, content, channel)
                        }
                    )
                    NavigationScreen.ANALYTICS -> AnalyticsScreen(
                        analyticsList = analytics
                    )
                }
            }
        }

        // Active Dialog Modals
        if (showAddPatientDialog) {
            AddEditPatientDialog(
                existingPatient = selectedPatient,
                onDismiss = { viewModel.closeAddPatientDialog() },
                onSave = { id, code, name, age, gender, contact, demographic, history, treatment ->
                    viewModel.savePatient(id, code, name, age, gender, contact, demographic, history, treatment)
                }
            )
        }

        if (showAddAppointmentDialog) {
            AddAppointmentDialog(
                patientsList = patients,
                onDismiss = { viewModel.closeAddAppointmentDialog() },
                onSave = { patientId, patientName, doctorName, dept, date, time, type, notes ->
                    viewModel.saveAppointment(patientId, patientName, doctorName, dept, date, time, type, notes)
                }
            )
        }

        if (showAddMedicationDialog) {
            AddMedicationDialog(
                patientsList = patients,
                onDismiss = { viewModel.closeAddMedicationDialog() },
                onSave = { patientId, patientName, name, dosage, frequency, qty, status ->
                    viewModel.saveMedication(patientId, patientName, name, dosage, frequency, qty, status)
                }
            )
        }

        if (showAddLabTestDialog) {
            AddLabTestDialog(
                patientsList = patients,
                onDismiss = { viewModel.closeAddLabTestDialog() },
                onSave = { patientId, patientName, testName, category, value, unit, range, status, interpretation, date ->
                    viewModel.saveLabTest(patientId, patientName, testName, category, value, unit, range, status, interpretation, date)
                }
            )
        }
    }
}
