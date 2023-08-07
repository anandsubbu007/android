package com.subbu.invoice.presentaion.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subbu.invoice.data.models.entity.Customer
import com.subbu.invoice.data.models.entity.Item
import com.subbu.invoice.data.repository.ItemRepo
import com.subbu.invoice.domain.repo.CustomerRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.subbu.invoice.utils.Result
import kotlinx.coroutines.launch

class SettingViewModel(
    private val usecase: SettingUseCase
) : ViewModel() {

    private var _fetchedCustomerState = MutableStateFlow<Result<List<Customer>>>(Result.Idle)
    val fetchedCustomerState: StateFlow<Result<List<Customer>>> = _fetchedCustomerState;
    private var _fetchedItemState = MutableStateFlow<Result<List<Item>>>(Result.Idle)
    val fetchedItemState: StateFlow<Result<List<Item>>> = _fetchedItemState;

    init {
        getAll()
    }

    fun getAll() {
        _fetchedCustomerState.value = Result.Loading;
        _fetchedItemState.value = Result.Loading;
        viewModelScope.launch {
            _fetchedCustomerState.value = usecase.getCustomers();
            _fetchedItemState.value = usecase.getItems();
        }
    }
}


data class SettingUseCase(
    val getCustomers: GetAllCustomers,
    val getItems: GetAllItems,
)

class GetAllCustomers(private val repo: CustomerRepo) {
    suspend operator fun invoke(): Result<List<Customer>> {
        return try {
            val datas = repo.getAll()
            Result.Success(datas)
        } catch (e: Exception) {
            Result.Error(e);
        }
    }
}

class GetAllItems(private val repo: ItemRepo) {
    suspend operator fun invoke(): Result<List<Item>> {
        return try {
            val datas = repo.getAll()
            Result.Success(datas)
        } catch (e: Exception) {
            Result.Error(e);
        }
    }
}