

package com.mystiq.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

import com.mystiq.data.local.dao.CategoryDao
import com.mystiq.data.local.dao.ClothingItemDao
import com.mystiq.data.local.dao.OutfitDao
import com.mystiq.data.local.dao.TagDao
import com.mystiq.data.local.dao.UserDao
import com.mystiq.data.local.dao.UserProfileDao
import com.mystiq.data.local.dao.WearLogDao
import com.mystiq.data.local.dao.WishlistDao

import com.mystiq.data.local.entity.CategoryEntity
import com.mystiq.data.local.entity.ClothingItemEntity
import com.mystiq.data.local.entity.ClothingItemTagEntity
import com.mystiq.data.local.entity.OutfitEntity
import com.mystiq.data.local.entity.OutfitItemEntity
import com.mystiq.data.local.entity.TagEntity
import com.mystiq.data.local.entity.UserEntity
import com.mystiq.data.local.entity.UserProfileEntity
import com.mystiq.data.local.entity.WearLogEntity
import com.mystiq.data.local.entity.WishlistItemEntity

@Database(
    entities = [
        UserEntity::class,
        UserProfileEntity::class,
        CategoryEntity::class,
        ClothingItemEntity::class,
        TagEntity::class,
        ClothingItemTagEntity::class,
        OutfitEntity::class,
        OutfitItemEntity::class,
        WishlistItemEntity::class,
        WearLogEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MystiqDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun userProfileDao(): UserProfileDao

    abstract fun categoryDao(): CategoryDao

    abstract fun clothingItemDao(): ClothingItemDao

    abstract fun tagDao(): TagDao

    abstract fun outfitDao(): OutfitDao

    abstract fun wishlistDao(): WishlistDao

    abstract fun wearLogDao(): WearLogDao
}



