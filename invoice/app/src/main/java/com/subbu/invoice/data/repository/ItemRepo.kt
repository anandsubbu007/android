package com.subbu.invoice.data.repository

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.subbu.invoice.data.dao.ItemDao
import com.subbu.invoice.data.models.entity.Item
import com.subbu.invoice.data.models.enums.Status

class ItemRepo(
    private val dao: ItemDao,
    private val pref: SharedPreferences
) {

    init {
        val isSettedUp = pref.getBoolean("ItemsDatas", false);
        if (!isSettedUp) {
            setUpDb();
            pref.edit {
                putBoolean("ItemsData", true);
            };
        }
    }


    fun getAll(): List<Item> {
        return dao.getAll();
    }

    fun get(id: Int): Item? {
        return dao.get(id);
    }

    suspend fun create(data: Item) {
        dao.insert(data)
    }

    suspend fun update(data: Item) {
        dao.update(data)
    }

    suspend fun delete(data: Item) {
        dao.delete(data)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }

    fun setUpDb() {
        Log.i("setUpDb", "Setting Up Customer Data For DB")
        dao.insertAll(
            arrayOf(
                Item(
                    id = 1,
                    name = "Rorito Pen",
                    price = 20.0F,
                    status = Status.Active,
                    tax = 0F,
                ),
                Item(
                    id = 2,
                    name = "Nataraj Pen",
                    price = 15.0F,
                    status = Status.Active,
                    tax = 0F,
                ),
                Item(
                    id = 3,
                    name = "Nataraj Pencil",
                    price = 5.0F,
                    status = Status.Active,
                    tax = 0F,
                ),
                Item(
                    id = 4,
                    name = "School Kit",
                    price = 50.0F,
                    status = Status.Active,
                    tax = 0F,
                ),
                Item(
                    id = 5,
                    name = "Drawing kit",
                    price = 200.0F,
                    status = Status.Active,
                    tax = 0F,
                ),
                Item(
                    id = 6,
                    name = "Geomentry Box",
                    price = 50.0F,
                    status = Status.Active,
                    tax = 0F,
                ),
                Item(
                    id = 7,
                    name = "Scale Box",
                    price = 100.0F,
                    status = Status.Active,
                    tax = 0F,
                ),
                Item(
                    id = 8,
                    name = "Ink Pen",
                    price = 60.0F,
                    status = Status.Active,
                    tax = 0F,
                ),
                Item(
                    id = 9,
                    name = "Zapper Pen",
                    price = 20.0F,
                    status = Status.Active,
                    tax = 0F,
                ),
            )
        )
    }

}