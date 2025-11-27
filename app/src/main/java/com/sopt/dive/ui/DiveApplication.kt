package com.sopt.dive.ui

import android.app.Application
import com.sopt.dive.core.network.ServicePool

class DiveApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        ServicePool.initialize(this)
    }
}
