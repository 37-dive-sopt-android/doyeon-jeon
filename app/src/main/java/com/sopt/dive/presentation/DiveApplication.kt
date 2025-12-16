package com.sopt.dive.presentation

import android.app.Application
import com.sopt.dive.di.ServicePool

class DiveApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        ServicePool.initialize(this)
    }
}
