package com.neurobharath.patientmanager.data.local.dao

import androidx.room.*
import com.neurobharath.patientmanager.data.local.entity.AnalyticsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnalyticsDao {
    @Query("SELECT * FROM analytics ORDER BY id DESC")
    fun getAllAnalytics(): Flow<List<AnalyticsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnalytics(analytics: AnalyticsEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnalyticsList(analyticsList: List<AnalyticsEntity>)

    @Update
    suspend fun updateAnalytics(analytics: AnalyticsEntity)

    @Delete
    suspend fun deleteAnalytics(analytics: AnalyticsEntity)
}
