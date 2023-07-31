package com.subbu.invoice.domain.repo

import com.subbu.invoice.data.models.entity.Customer

interface CustomerRepo {
    fun getAll(): List<Customer>
    fun get(id: Int): Customer
    suspend fun create(data: Customer)
    suspend fun update(data: Customer)
    suspend fun delete(data: Customer)
    suspend fun deleteAll()
}