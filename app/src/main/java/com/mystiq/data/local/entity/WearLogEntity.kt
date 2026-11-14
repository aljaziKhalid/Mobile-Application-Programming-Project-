package com.mystiq.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "wear_logs",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ClothingItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["clothingItemId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = OutfitEntity::class,
            parentColumns = ["outfitId"],
            childColumns = ["outfitId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["userId"]),
        Index(value = ["clothingItemId"]),
        Index(value = ["outfitId"])
    ]
)
data class WearLogEntity(
    @PrimaryKey(autoGenerate = true)
    val wearLogId: Int = 0,
    val userId: Long,
    val clothingItemId: Int,
    val outfitId: Int? = null,
    val wornAtEpochMillis: Long
)