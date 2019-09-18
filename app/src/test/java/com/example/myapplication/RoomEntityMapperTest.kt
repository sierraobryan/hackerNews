package com.example.myapplication

import com.example.myapplication.data.model.Item
import com.example.myapplication.data.room.RoomEntityMapper
import com.example.myapplication.data.model.RoomItem
import org.junit.Assert
import org.junit.Test

class RoomEntityMapperTest {

    private val mapper: RoomEntityMapper =
        RoomEntityMapper

    @Test
    fun testItemToRoomItem() {
        val expected = RoomItem(
            1,
            false,
            "story",
            "name",
            1,
            false,
            2,
            "",
            "",
            "",
            1,
            1,
            false,
            false)

        val item = Item(1, false, "story", "name", 1, false, listOf(1), 2, "", "", "", listOf(1), 1, 1)

        val actual = mapper.itemToRoomItem(item)
        Assert.assertEquals(expected, actual)
    }

}