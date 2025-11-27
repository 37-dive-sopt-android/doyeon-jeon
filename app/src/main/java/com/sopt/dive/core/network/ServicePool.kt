package com.sopt.dive.core.network

import android.content.Context
import com.sopt.dive.data.datasource.local.DataStoreDataSource
import com.sopt.dive.data.datasource.local.DataStoreDataSourceImpl
import com.sopt.dive.data.datasource.local.dataStore
import com.sopt.dive.data.datasource.remote.auth.AuthDataSource
import com.sopt.dive.data.datasource.remote.auth.AuthDataSourceImpl
import com.sopt.dive.data.datasource.remote.user.UserDataSource
import com.sopt.dive.data.datasource.remote.user.UserDataSourceImpl
import com.sopt.dive.data.repository.auth.AuthRepository
import com.sopt.dive.data.repository.auth.AuthRepositoryImpl
import com.sopt.dive.data.repository.user.UserRepository
import com.sopt.dive.data.repository.user.UserRepositoryImpl
import com.sopt.dive.data.service.AuthService
import com.sopt.dive.data.service.UserService

object ServicePool {
    private lateinit var applicationContext: Context

    fun initialize(context: Context) {
        applicationContext = context.applicationContext
    }

    private val authService: AuthService by lazy {
        ApiFactory.create<AuthService>()
    }

    private val userService: UserService by lazy {
        ApiFactory.create<UserService>()
    }

    private val authDataSource: AuthDataSource by lazy {
        AuthDataSourceImpl(authService)
    }

    private val userDataSource: UserDataSource by lazy {
        UserDataSourceImpl(userService)
    }

    private val dataStoreDataSource: DataStoreDataSource by lazy {
        DataStoreDataSourceImpl(applicationContext.dataStore)
    }

    val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(
            dataStoreDataSource = dataStoreDataSource,
            authDataSource = authDataSource,
        )
    }

    val userRepository: UserRepository by lazy {
        UserRepositoryImpl(
            dataStoreDataSource = dataStoreDataSource,
            userDataSource = userDataSource
        )
    }
}
