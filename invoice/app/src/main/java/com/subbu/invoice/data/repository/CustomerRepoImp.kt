package com.subbu.invoice.data.repository

import com.subbu.invoice.data.dao.CustomerDao
import com.subbu.invoice.data.models.entity.Customer
import com.subbu.invoice.domain.repo.CustomerRepo

class CustomerRepoImp(
    private val dao: CustomerDao
) : CustomerRepo {
    override fun getAll(): List<Customer> {
//        return dao.getAll()
        return  emptyList()
;    }

    override fun get(id: Int): Customer {
        TODO("Not yet implemented")
    }


    override suspend fun create(data: Customer) {
//        dao.create(data)
    }

    override suspend fun update(data: Customer) {
//        dao.update(data)
    }

    override suspend fun delete(data: Customer) {
        dao.delete(data)
    }

    override suspend fun deleteAll() {
//        dao.deleteAll()
    }
}