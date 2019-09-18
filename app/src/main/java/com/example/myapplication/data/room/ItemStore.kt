package com.example.myapplication.data.room

import com.example.myapplication.Mockable
import com.example.myapplication.data.model.Item
import com.example.myapplication.data.model.RoomItem
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

@Mockable
class ItemStore (
    private val database: ItemDatabase,
    private val itemDao: ItemDao = database.itemDao()
) {

    fun insertItems(items: List<Item>) :Completable {
        return Completable.fromCallable {
            val roomItems = items.map {
                RoomEntityMapper.itemToRoomItem(
                    it
                )
            }
            itemDao.insertItems(roomItems)
        }
            .subscribeOn(Schedulers.io())
    }

}

object RoomEntityMapper {

    fun itemToRoomItem(item: Item) =
        RoomItem(
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