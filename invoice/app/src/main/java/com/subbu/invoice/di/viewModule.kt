package com.subbu.invoice.di

import org.koin.androidx.viewmodel.dsl.viewModel
import com.subbu.invoice.presentaion.home.HomeViewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { HomeViewModel() }
}