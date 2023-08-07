package com.subbu.invoice.presentaion.home

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.subbu.invoice.data.repository.CustomerRepoImp
import com.subbu.invoice.domain.repo.CustomerRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(
    private val customerRepo: CustomerRepo
) : ViewModel() {
    lateinit var tabNavController: NavHostController;
    lateinit var AppNavController: NavController;
}


