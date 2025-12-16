package com.sopt.dive.presentation

import android.app.Application
import com.sopt.dive.di.core.DataStoreModule

class DiveApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        DataStoreModule.initialize(this)
    }
}
