package com.subbu.invoice

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Invoice: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Invoice);
        }
    }
}