package com.mystiq.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mystiq.data.local.entity.OutfitEntity
import com.mystiq.data.local.entity.OutfitItemEntity
import com.mystiq.data.local.entity.OutfitWithItems
import kotlinx.coroutines.flow.Flow

@Dao
interface OutfitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOutfit(outfit: OutfitEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOutfitItems(items: List<OutfitItemEntity>)

    @Update
    suspend fun updateOutfit(outfit: OutfitEntity)

    @Delete
    suspend fun deleteOutfit(outfit: OutfitEntity)

    @Query("DELETE FROM outfit_items WHERE outfitId = :outfitId")
    suspend fun clearOutfitItems(outfitId: Long)

    @Transaction
    @Query("SELECT * FROM outfits WHERE userId = :userId ORDER BY createdAt DESC")
    fun getOutfitsByUser(userId: Long): Flow<List<OutfitWithItems>>

    @Transaction
    @Query("SELECT * FROM outfits WHERE outfitId = :outfitId LIMIT 1")
    suspend fun getOutfitById(outfitId: Long): OutfitWithItems?

    // Save an outfit and its items together (all or nothing)
    @Transaction
    suspend fun saveOutfit(outfit: OutfitEntity, clothingItemIds: List<Long>): Long {
        val outfitId = insertOutfit(outfit)
        insertOutfitItems(clothingItemIds.map { OutfitItemEntity(outfitId, it) })
        return outfitId
    }

    // Replace the items of an existing outfit
    @Transaction
    suspend fun replaceOutfitItems(outfitId: Long, clothingItemIds: List<Long>) {
        clearOutfitItems(outfitId)
        insertOutfitItems(clothingItemIds.map { OutfitItemEntity(outfitId, it) })
    }
}