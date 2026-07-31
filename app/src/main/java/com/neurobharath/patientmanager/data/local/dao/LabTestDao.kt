package com.neurobharath.patientmanager.data.local.dao

import androidx.room.*
import com.neurobharath.patientmanager.data.local.entity.LabTestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LabTestDao {
    @Query("SELECT * FROM lab_tests ORDER BY id DESC")
    fun getAllLabTests(): Flow<List<LabTestEntity>>

    @Query("SELECT * FROM lab_tests WHERE patient_id = :patientId")
    fun getLabTestsForPatient(patientId: Int): Flow<List<LabTestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLabTest(labTest: LabTestEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLabTests(labTests: List<LabTestEntity>)

    @Update
    suspend fun updateLabTest(labTest: LabTestEntity)

    @Delete
    suspend fun deleteLabTest(labTest: LabTestEntity)
}
