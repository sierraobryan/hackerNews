package com.example.myapplication.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.example.myapplication.data.model.RoomItem

@Database(entities = [RoomItem::class],
    version = 1,
    exportSchema = false)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun itemDao() : ItemDao

    companion object {
        private var INSTANCE : ItemDatabase? = null

        fun getInstance(context: Context) : ItemDatabase? {
            if (INSTANCE == null) {
                synchronized(ItemDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ItemDatabase::class.java,
                        "item.db"
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}

object DatabaseMigrations {
    val migrations: List<Migration> = listOf()
}