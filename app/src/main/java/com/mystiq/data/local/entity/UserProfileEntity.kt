package com.mystiq.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_profiles",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId"], unique = true)]
)
data class UserProfileEntity(

    @PrimaryKey(autoGenerate = true)
    val profileId: Long = 0,

    val userId: Long,

    val fullName: String,

    val height: Float? = null,

    val gender: String? = null,

    val bodyShape: String? = null
)