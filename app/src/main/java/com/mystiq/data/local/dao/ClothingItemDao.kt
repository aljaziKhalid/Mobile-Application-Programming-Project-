package com.mystiq.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mystiq.data.local.entity.ClothingItemEntity

@Dao
interface ClothingItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ClothingItemEntity): Long

    @Update
    suspend fun updateItem(item: ClothingItemEntity)

    @Delete
    suspend fun deleteItem(item: ClothingItemEntity)

    @Query("SELECT * FROM clothing_items ORDER BY id DESC")
    suspend fun getAllItems(): List<ClothingItemEntity>

    @Query("SELECT * FROM clothing_items WHERE isArchived = 0 ORDER BY id DESC")
    suspend fun getActiveItems(): List<ClothingItemEntity>
}