package com.mystiq.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val categoryId: Int = 0,
    val userId: Long,
    val name: String
)