package com.mystiq.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mystiq.data.local.entity.TagEntity

@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(tag: TagEntity): Long

    @Update
    suspend fun updateTag(tag: TagEntity)

    @Delete
    suspend fun deleteTag(tag: TagEntity)

    @Query("SELECT * FROM tags WHERE userId = :userId ORDER BY name")
    suspend fun getTagsByUser(userId: Int): List<TagEntity>

    @Query("SELECT * FROM tags WHERE tagId = :tagId")
    suspend fun getTagById(tagId: Int): TagEntity?
}