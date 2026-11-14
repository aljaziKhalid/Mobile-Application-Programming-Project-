package com.mystiq.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Table: wishlist_items
 * One row = one item the user wants to buy (shown on the Wishlist screen).
 *
 * PK:  wishlistId (Int)
 * FKs: userId -> UserEntity.userId (Long)
 *      itemId -> ClothingItemEntity.id (Int)
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
        ),
        // Optional link to a clothing item in the closet (e.g. "similar to this item",
        // or the item after it was bought). Deleting that item keeps the wish.
        ForeignKey(
            entity = ClothingItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["itemId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index("userId"), Index("itemId")]
)
data class WishlistItemEntity(
    @PrimaryKey(autoGenerate = true)
    val wishlistId: Int = 0,
    val userId: Long,
    val itemId: Int? = null,             // nullable: most wishes are not in the closet yet
    val name: String,
    val category: String? = null,        // shown under the name, e.g. "Shoes"
    val imageUri: String? = null,        // path of the image saved in app storage
    val createdAt: Long = System.currentTimeMillis()
)