package com.subbu.invoice

import android.app.Application
import com.subbu.invoice.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Invoice: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
//            androidLogger()
            androidContext(this@Invoice);
            modules(appModule)
        }
    }
}