package com.example.myapplication.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.example.myapplication.data.model.RoomItem

@Database(entities = [RoomItem::class],
    version = 1,
    exportSchema = false)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun itemDao() : ItemDao
}

object DatabaseMigrations {
    val migrations: List<Migration> = listOf()
}