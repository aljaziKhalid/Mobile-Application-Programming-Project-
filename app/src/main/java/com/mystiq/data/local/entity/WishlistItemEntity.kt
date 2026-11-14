package com.mystiq.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Table: wishlist_items
 * One row = one item the user wants to buy (shown on the Wishlist screen).
 */
@Entity(
    tableName = "wishlist_items",
    foreignKeys = [
        // Each wishlist item belongs to one user. Deleting the user deletes their wishlist.
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
        // TODO: add the CategoryEntity foreign key here when CategoryEntity is ready
    ],
    indices = [Index("userId"), Index("categoryId")]
)
data class WishlistItemEntity(
    @PrimaryKey(autoGenerate = true)
    val wishlistItemId: Long = 0,
    val userId: Long,
    val categoryId: Long? = null,
    val name: String,
    val imageUri: String? = null,        // path of the image saved in app storage
    val createdAt: Long = System.currentTimeMillis()
)