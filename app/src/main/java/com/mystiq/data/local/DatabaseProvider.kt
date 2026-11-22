
package com.mystiq.data.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    @Volatile
    private var INSTANCE: MystiqDatabase? = null

    fun getDatabase(context: Context): MystiqDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                MystiqDatabase::class.java,
                "mystiq_database"
            ).build()

            INSTANCE = instance
            instance
        }
    }
}
