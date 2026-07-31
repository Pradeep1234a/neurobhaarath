package com.neurobharath.patientmanager.data.local.dao

import androidx.room.*
import com.neurobharath.patientmanager.data.local.entity.CommunicationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CommunicationDao {
    @Query("SELECT * FROM communication ORDER BY id DESC")
    fun getAllCommunications(): Flow<List<CommunicationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommunication(communication: CommunicationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommunications(communications: List<CommunicationEntity>)

    @Update
    suspend fun updateCommunication(communication: CommunicationEntity)

    @Delete
    suspend fun deleteCommunication(communication: CommunicationEntity)
}
