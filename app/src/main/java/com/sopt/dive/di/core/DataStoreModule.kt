package com.sopt.dive.di.core

import android.content.Context
import com.sopt.dive.data.local.datasource.DataStoreDataSource
import com.sopt.dive.data.local.datasourceimpl.DataStoreDataSourceImpl
import com.sopt.dive.data.local.datasourceimpl.dataStore

object DataStoreModule {
    private lateinit var applicationContext: Context

    fun initialize(context: Context) {
        applicationContext = context.applicationContext
    }

    val dataStoreDataSource: DataStoreDataSource by lazy {
        DataStoreDataSourceImpl(applicationContext.dataStore)
    }
}