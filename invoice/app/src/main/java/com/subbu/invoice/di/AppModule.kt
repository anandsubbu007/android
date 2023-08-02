package com.subbu.invoice.di

import org.koin.dsl.module

val appModule = module {

    includes(databaseModule,
        viewModelModules,
        RepositoryModule,
        )

}