package com.mystiq.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [Index(value = ["email"], unique = true)]
)
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    val userId: Long = 0,

    val username: String,

    val email: String,

    val createdAt: Long = System.currentTimeMillis()
)