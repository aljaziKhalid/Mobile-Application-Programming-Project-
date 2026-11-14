package com.mystiq.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mystiq.data.local.entity.WearLogEntity
import kotlinx.coroutines.flow.Flow

data class WearTrackerRow(
    val clothingItemId: Int,
    val name: String,
    val color: String,
    val imageUri: String,
    val categoryId: Int,
    val wearCount: Int,
    val lastWornEpochMillis: Long?
)

data class CategoryCountStat(
    val categoryId: Int,
    val itemCount: Int
)

data class ColorCountStat(
    val color: String,
    val itemCount: Int
)

@Dao
interface WearLogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWearLog(log: WearLogEntity): Long

    @Query("DELETE FROM wear_logs WHERE wearLogId = :wearLogId")
    suspend fun deleteWearLogById(wearLogId: Int)

    @Query(
        """
        SELECT 
            c.id AS clothingItemId,
            c.name AS name,
            c.color AS color,
            c.imageUri AS imageUri,
            c.categoryId AS categoryId,
            COUNT(w.wearLogId) AS wearCount,
            MAX(w.wornAtEpochMillis) AS lastWornEpochMillis
        FROM clothing_items c
        LEFT JOIN wear_logs w ON c.id = w.clothingItemId
        WHERE c.isArchived = 0
        GROUP BY c.id
        ORDER BY wearCount DESC, lastWornEpochMillis DESC
        """
    )
    fun observeWearTrackerItems(): Flow<List<WearTrackerRow>>

    @Query(
        """
        SELECT 
            c.id AS clothingItemId,
            c.name AS name,
            c.color AS color,
            c.imageUri AS imageUri,
            c.categoryId AS categoryId,
            COUNT(w.wearLogId) AS wearCount,
            MAX(w.wornAtEpochMillis) AS lastWornEpochMillis
        FROM clothing_items c
        LEFT JOIN wear_logs w ON c.id = w.clothingItemId
        WHERE c.isArchived = 0
        GROUP BY c.id
        HAVING lastWornEpochMillis IS NULL OR lastWornEpochMillis < :thresholdMillis
        """
    )
    fun observeNotWornSince(thresholdMillis: Long): Flow<List<WearTrackerRow>>

    @Query(
        """
        SELECT categoryId AS categoryId, COUNT(*) AS itemCount
        FROM clothing_items
        WHERE isArchived = 0
        GROUP BY categoryId
        """
    )
    fun observeCategoryCounts(): Flow<List<CategoryCountStat>>

    @Query(
        """
        SELECT color AS color, COUNT(*) AS itemCount
        FROM clothing_items
        WHERE isArchived = 0
        GROUP BY color
        ORDER BY itemCount DESC
        """
    )
    fun observeColorCounts(): Flow<List<ColorCountStat>>
}