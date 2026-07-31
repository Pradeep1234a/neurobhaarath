package com.neurobharath.patientmanager.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.neurobharath.patientmanager.data.local.dao.*
import com.neurobharath.patientmanager.data.local.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        PatientEntity::class,
        AppointmentEntity::class,
        MedicationEntity::class,
        LabTestEntity::class,
        CommunicationEntity::class,
        AnalyticsEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun patientDao(): PatientDao
    abstract fun appointmentDao(): AppointmentDao
    abstract fun medicationDao(): MedicationDao
    abstract fun labTestDao(): LabTestDao
    abstract fun communicationDao(): CommunicationDao
    abstract fun analyticsDao(): AnalyticsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "neuro_bharath_db"
                )
                .fallbackToDestructiveMigration()
                .addCallback(AppDatabaseCallback(scope))
                .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            scope.launch(Dispatchers.IO) {
                var attempts = 0
                while (INSTANCE == null && attempts < 10) {
                    kotlinx.coroutines.delay(100)
                    attempts++
                }
                INSTANCE?.let { database ->
                    populateInitialData(database)
                }
            }
        }

        suspend fun populateInitialData(db: AppDatabase) {
            val patientDao = db.patientDao()
            val appointmentDao = db.appointmentDao()
            val medicationDao = db.medicationDao()
            val labTestDao = db.labTestDao()
            val commDao = db.communicationDao()
            val analyticsDao = db.analyticsDao()

            // 1. Initial Patients
            val p1Id = patientDao.insertPatient(
                PatientEntity(
                    patientCode = "NB-1001",
                    name = "Rajesh Sharma",
                    age = 54,
                    gender = "Male",
                    contact = "+91 98765 43210",
                    demographic = "Bangalore, Karnataka (A+ Positive)",
                    medicalHistory = "Type 2 Diabetes, Mild Hypertension, Chronic Migraines",
                    treatmentInformation = "Levetiracetam 500mg, Regular Neuro checkups every 3 months"
                )
            ).toInt()

            val p2Id = patientDao.insertPatient(
                PatientEntity(
                    patientCode = "NB-1002",
                    name = "Ananya Iyer",
                    age = 32,
                    gender = "Female",
                    contact = "+91 91234 56789",
                    demographic = "Chennai, Tamil Nadu (O+ Positive)",
                    medicalHistory = "Epilepsy (Partial Seizures), Vitamin D Deficiency",
                    treatmentInformation = "Sodium Valproate 300mg BD, EEG Monitoring scheduled"
                )
            ).toInt()

            val p3Id = patientDao.insertPatient(
                PatientEntity(
                    patientCode = "NB-1003",
                    name = "Vikram Patil",
                    age = 67,
                    gender = "Male",
                    contact = "+91 99887 76655",
                    demographic = "Hubballi, Karnataka (B+ Positive)",
                    medicalHistory = "Early-stage Parkinson's Disease, Tremors",
                    treatmentInformation = "Levodopa/Carbidopa 100/25mg, Physical therapy twice weekly"
                )
            ).toInt()

            val p4Id = patientDao.insertPatient(
                PatientEntity(
                    patientCode = "NB-1004",
                    name = "Priya Deshmukh",
                    age = 29,
                    gender = "Female",
                    contact = "+91 98450 11223",
                    demographic = "Pune, Maharashtra (AB+ Positive)",
                    medicalHistory = "Multiple Sclerosis (Relapsing-Remitting)",
                    treatmentInformation = "Interferon beta-1a, Bi-monthly MRI scans"
                )
            ).toInt()

            // 2. Initial Appointments
            appointmentDao.insertAppointments(
                listOf(
                    AppointmentEntity(
                        patientId = p1Id,
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
                        patientId = p2Id,
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
                        patientId = p3Id,
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
            )

            // 3. Initial Medications
            medicationDao.insertMedications(
                listOf(
                    MedicationEntity(
                        patientId = p1Id,
                        patientName = "Rajesh Sharma",
                        name = "Keppra (Levetiracetam)",
                        quantity = 45,
                        prescriptionStatus = "Active",
                        dosage = "500 mg",
                        frequency = "Twice Daily (Morning / Night)"
                    ),
                    MedicationEntity(
                        patientId = p2Id,
                        patientName = "Ananya Iyer",
                        name = "Epilim (Sodium Valproate)",
                        quantity = 10,
                        prescriptionStatus = "Refill Needed",
                        dosage = "300 mg",
                        frequency = "Twice Daily"
                    ),
                    MedicationEntity(
                        patientId = p3Id,
                        patientName = "Vikram Patil",
                        name = "Sinemet (Levodopa/Carbidopa)",
                        quantity = 60,
                        prescriptionStatus = "Active",
                        dosage = "100/25 mg",
                        frequency = "Three times daily after meals"
                    )
                )
            )

            // 4. Initial Lab Tests
            labTestDao.insertLabTests(
                listOf(
                    LabTestEntity(
                        patientId = p1Id,
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
                        patientId = p2Id,
                        patientName = "Ananya Iyer",
                        testName = "Serum Valproic Acid Level",
                        category = "Blood Work",
                        resultValue = "42.5",
                        unit = "µg/mL",
                        referenceRange = "50.0 - 100.0",
                        status = "Critical",
                        interpretation = "Sub-therapeutic concentration. Dosage adjustment required.",
                        date = "2026-07-29"
                    ),
                    LabTestEntity(
                        patientId = p4Id,
                        patientName = "Priya Deshmukh",
                        testName = "Lumbar Puncture Oligoclonal Bands",
                        category = "Neurology",
                        resultValue = "Processing",
                        unit = "Index",
                        referenceRange = "Negative",
                        status = "Pending",
                        interpretation = "Analysis under process at Neuro-immunology lab.",
                        date = "2026-07-30"
                    )
                )
            )

            // 5. Initial Communication
            commDao.insertCommunications(
                listOf(
                    CommunicationEntity(
                        senderId = 101,
                        senderName = "Dr. K. S. Bharath",
                        recipientId = p1Id,
                        recipientName = "Rajesh Sharma",
                        patientId = p1Id,
                        content = "Please ensure blood sugar logs are brought to your upcoming OPD appointment on Aug 2.",
                        channelType = "Direct Message",
                        timestamp = "2026-07-31 09:15 AM"
                    ),
                    CommunicationEntity(
                        senderId = 102,
                        senderName = "Dr. Meera Nambiar",
                        recipientId = p2Id,
                        recipientName = "Ananya Iyer",
                        patientId = p2Id,
                        content = "Valproate levels came back slightly low. Please increase morning dose to 400mg as discussed.",
                        channelType = "Email / Clinical Note",
                        timestamp = "2026-07-30 04:45 PM"
                    )
                )
            )

            // 6. Initial Analytics
            analyticsDao.insertAnalyticsList(
                listOf(
                    AnalyticsEntity(
                        patientId = p1Id,
                        metric = "Record Management Efficiency",
                        value = 28.5,
                        category = "KPI Goal (+25% achieved)",
                        timestamp = "2026-07-31"
                    ),
                    AnalyticsEntity(
                        patientId = p2Id,
                        metric = "Scheduling Error Reduction",
                        value = 52.0,
                        category = "KPI Goal (-50% achieved)",
                        timestamp = "2026-07-31"
                    ),
                    AnalyticsEntity(
                        patientId = p3Id,
                        metric = "Patient Action & Engagement",
                        value = 34.2,
                        category = "KPI Goal (+30% achieved)",
                        timestamp = "2026-07-31"
                    )
                )
            )
        }
    }
}
