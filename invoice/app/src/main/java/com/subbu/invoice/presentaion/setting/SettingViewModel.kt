package com.subbu.invoice.presentaion.setting

import androidx.lifecycle.ViewModel
import com.subbu.invoice.data.models.entity.Customer
import com.subbu.invoice.domain.repo.CustomerRepo

class SettingViewModel() : ViewModel() {

//private val usecase: SettingUseCase

}


data class SettingUseCase(
    val getCustomers: GetAllCustomers,
    val getItems: GetAllItems,
)

class GetAllCustomers(private val repo: CustomerRepo) {
    suspend operator fun invoke(): Result<List<Customer>> {
        return try {
            val notes = repo.getAll()
            Result.success(notes)
        } catch (e: Exception) {
            Result.failure(e);
        }
    }
}

class GetAllItems(private val repo: CustomerRepo) {
    suspend operator fun invoke(): Result<List<Customer>> {
        return try {
            val notes = repo.getAll()
            Result.success(notes)
        } catch (e: Exception) {
            Result.failure(e);
        }
    }
}