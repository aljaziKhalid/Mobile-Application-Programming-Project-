
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
    suspend fun clearOutfitItems(outfitId: Int)

    @Query("SELECT * FROM outfits ORDER BY outfitId DESC")
    suspend fun getAllOutfits(): List<OutfitEntity>

    @Query("SELECT * FROM outfits WHERE outfitId = :outfitId LIMIT 1")
    suspend fun getOutfitById(outfitId: Int): OutfitEntity?

    @Transaction
    suspend fun saveOutfit(
        outfit: OutfitEntity,
        clothingItemIds: List<Int>
    ): Long {
        val outfitId = insertOutfit(outfit).toInt()

        insertOutfitItems(
            clothingItemIds.map { itemId ->
                OutfitItemEntity(outfitId, itemId)
            }
        )

        return outfitId.toLong()
    }

    @Transaction
    suspend fun replaceOutfitItems(
        outfitId: Int,
        clothingItemIds: List<Int>
    ) {
        clearOutfitItems(outfitId)

        insertOutfitItems(
            clothingItemIds.map { itemId ->
                OutfitItemEntity(outfitId, itemId)
            }
        )
    }
}
