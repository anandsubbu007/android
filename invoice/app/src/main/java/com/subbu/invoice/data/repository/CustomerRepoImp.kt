package com.subbu.invoice.data.repository

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.subbu.invoice.data.dao.CustomerDao
import com.subbu.invoice.data.models.entity.Customer
import com.subbu.invoice.domain.repo.CustomerRepo

class CustomerRepoImp(
    private val dao: CustomerDao,
    private val pref: SharedPreferences
) : CustomerRepo {

    init {
        val isSettedUp = pref.getBoolean("CustomerData", false);
        if (!isSettedUp) {
            setUpDb();
            pref.edit {
                putBoolean("CustomerData", true);
            };
        }
    }


    override fun getAll(): List<Customer> {
        return dao.getAll();
    }

    override fun get(id: Int): Customer? {
        return dao.get(id);
    }

    override suspend fun create(data: Customer) {
        dao.insert(data)
    }

    override suspend fun update(data: Customer) {
        dao.update(data)
    }

    override suspend fun delete(data: Customer) {
        dao.delete(data)
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    override fun setUpDb() {
        Log.i("setUpDb", "Setting Up Customer Data For DB")
        dao.insertAll(
            arrayOf(
                Customer(
                    name = "Cash Sale",
                    details = "",
                    gst = "",
                    id = 1,
                    mobileNo = "89865656565"
                ),
                Customer(
                    name = "Anand",
                    details = "",
                    gst = "3664864sdf6af",
                    id = 0,
                    mobileNo = "89865656565"
                ),
                Customer(
                    name = "Subbu",
                    details = "",
                    gst = "366485sdf6af",
                    id = 2,
                    mobileNo = "777865656565"
                ),
                Customer(
                    name = "Muthu",
                    details = "",
                    gst = "3sdfasdsdf6af",
                    id = 3,
                    mobileNo = "11165656565"
                ),
                Customer(
                    name = "Aravind",
                    details = "",
                    gst = "36OOIJSDNdf6af",
                    id = 4,
                    mobileNo = "55555656565"
                ),
            )
        )
    }

}