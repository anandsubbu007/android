package com.subbu.invoice.di

import androidx.room.Room
import com.subbu.invoice.data.database.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(

            androidApplication(), AppDatabase::class.java, "db.db"
        )

    }
    single { get<AppDatabase>().invoice() }
    single { get<AppDatabase>().customers() }
    single { get<AppDatabase>().items() }
    single { get<AppDatabase>().transactions() }
}