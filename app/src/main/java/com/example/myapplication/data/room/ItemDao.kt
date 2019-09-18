package com.example.myapplication.data.room

import androidx.room.*
import com.example.myapplication.data.model.RoomItem
import io.reactivex.Flowable

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(roomItem: RoomItem): Long

    @Update
    fun updateItem(item: RoomItem)

    @Query("Select * FROM item WHERE id = :id")
    fun findItemById(id: Int): RoomItem

    @Query("SELECT * FROM item where parent = :parentId")
    fun findKidsByParentId(parentId: Int): Flowable<List<RoomItem>>

    @Query("SELECT * FROM item where favorite = 1")
    fun findFavorites(): Flowable<List<RoomItem>>

    @Query("SELECT * FROM item where viewed = 1")
    fun findViewedHistory(): Flowable<List<RoomItem>>

    @Transaction
    fun insertItems(items: List<RoomItem>) {
        items.forEach { upsertItem(it) }
    }

    private fun upsertItem(item: RoomItem) {
        val id = insertItem(item).toInt()
        if (id == -1) {
            updateItem(item)
        }
    }

}