package com.mystiq.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mystiq.data.local.entity.WishlistItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WishlistDao {

    // Read: all wishlist items of one user, newest first.
    // Flow = the screen updates automatically when the table changes.
    @Query("SELECT * FROM wishlist_items WHERE userId = :userId ORDER BY createdAt DESC")
    fun getWishlistItems(userId: Long): Flow<List<WishlistItemEntity>>

    // Read: one item by its id
    @Query("SELECT * FROM wishlist_items WHERE wishlistId = :wishlistId")
    suspend fun getById(wishlistId: Int): WishlistItemEntity?

    // Insert: returns the new row id
    @Insert
    suspend fun insert(item: WishlistItemEntity): Long

    // Update
    @Update
    suspend fun update(item: WishlistItemEntity)

    // Delete
    @Delete
    suspend fun delete(item: WishlistItemEntity)

    // Delete by id (used when the heart is tapped on the Wishlist screen)
    @Query("DELETE FROM wishlist_items WHERE wishlistId = :wishlistId")
    suspend fun deleteById(wishlistId: Int)
}