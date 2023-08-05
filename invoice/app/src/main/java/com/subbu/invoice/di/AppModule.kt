package com.subbu.invoice.di

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.subbu.invoice.data.database.AppDatabase
import com.subbu.invoice.data.repository.CustomerRepoImp
import com.subbu.invoice.domain.repo.CustomerRepo
import com.subbu.invoice.presentaion.home.HomeViewModel
import com.subbu.invoice.presentaion.setting.GetAllCustomers
import com.subbu.invoice.presentaion.setting.GetAllItems
import com.subbu.invoice.presentaion.setting.SettingUseCase
import com.subbu.invoice.presentaion.setting.SettingViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Log.i("ROOM", "On INIT")
        Room.databaseBuilder(
            androidApplication(), AppDatabase::class.java, "db.db"
        ).allowMainThreadQueries()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Log.i("ROOM", "On Create")
                }
            }).build();

    }
    single { get<AppDatabase>().invoice() }
    single { get<AppDatabase>().customers() }
    single { get<AppDatabase>().items() }
    single { get<AppDatabase>().transactions() }
    single { getSharedPrefs(androidApplication()) }

// Repos
    single<CustomerRepo> { CustomerRepoImp(get()) }

// ViewModels
    viewModel { HomeViewModel(get<CustomerRepo>()) }
    viewModel { SettingViewModel() }


// Usecases
    factory { GetAllCustomers(get()) }
    factory { GetAllItems(get()) }

    single {
        SettingUseCase(
            getCustomers = get(),
            getItems = get(),
        )
    }
}


fun getSharedPrefs(androidApplication: Application): SharedPreferences {
    return androidApplication.getSharedPreferences("default", android.content.Context.MODE_PRIVATE)
}

