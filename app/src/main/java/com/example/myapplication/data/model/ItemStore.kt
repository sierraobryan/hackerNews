package com.example.myapplication.data.model

import com.example.myapplication.data.room.ItemDao
import com.example.myapplication.data.room.ItemDatabase
import dagger.Provides
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class ItemStore (
    private val database: ItemDatabase,
    private val itemDao: ItemDao = database.itemDao()
) {

    fun insertItems(items: List<Item>) :Completable {
        return Completable.fromCallable {
            val roomItems = items.map { RoomEntityMapper.ItemToRoomItem(it) }
            itemDao.insertItems(roomItems)
        }
            .subscribeOn(Schedulers.io())
    }

}

object RoomEntityMapper {

    fun ItemToRoomItem(item: Item) = RoomItem(
        item.id,
        item.isDeleted,
        item.type,
        item.authorName,
        item.publishDate,
        item.isDead,
        item.parent,
        item.text,
        item.url,
        item.title,
        item.score,
        item.descendants
    )
}