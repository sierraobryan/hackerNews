package com.example.myapplication.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class RoomItem(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "deleted") val isDeleted: Boolean = false,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "by") val authorName: String?,
    @ColumnInfo(name = "time") val publishDate: Int?,
    @ColumnInfo(name = "dead") val isDead: Boolean = false,
    @ColumnInfo(name = "parent") val parent: Int?,
    @ColumnInfo(name = "text") val text: String?,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "score") val score: Int?,
    @ColumnInfo(name = "descendants") val descendants: Int = 0,
    @ColumnInfo(name = "favorite") val isFavorite: Boolean = false,
    @ColumnInfo(name = "viewed") val viewed: Boolean = false
)