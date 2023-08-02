package com.subbu.invoice.di

import com.subbu.invoice.data.repository.CustomerRepoImp
import com.subbu.invoice.domain.repo.CustomerRepo
import org.koin.dsl.module

val RepositoryModule = module {
single<CustomerRepo> { CustomerRepoImp(get()) }

}