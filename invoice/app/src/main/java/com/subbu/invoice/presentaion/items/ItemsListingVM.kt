package com.subbu.invoice.presentaion.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subbu.invoice.data.models.entity.Item
import com.subbu.invoice.presentaion.setting.SettingUseCase
import com.subbu.invoice.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemsListingVM(val usecase: SettingUseCase) : ViewModel() {
    private var _fetchedItemState = MutableStateFlow<Result<List<Item>>>(Result.Idle)
    val fetchedItemState: StateFlow<Result<List<Item>>> = _fetchedItemState;

    init {
        getAll()
    }

    fun getAll() {
        _fetchedItemState.value = Result.Loading;
        viewModelScope.launch {
            _fetchedItemState.value = usecase.getItems();
        }
    }

}
