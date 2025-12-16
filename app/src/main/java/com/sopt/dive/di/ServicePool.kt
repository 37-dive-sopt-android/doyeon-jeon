package com.sopt.dive.di

import android.content.Context
import com.sopt.dive.core.network.ApiFactory
import com.sopt.dive.data.datasource.local.datasource.DataStoreDataSource
import com.sopt.dive.data.datasource.local.datasourceimpl.DataStoreDataSourceImpl
import com.sopt.dive.data.datasource.local.datasourceimpl.dataStore
import com.sopt.dive.data.datasource.remote.datasource.AuthDataSource
import com.sopt.dive.data.datasource.remote.datasource.ReqresDataSource
import com.sopt.dive.data.datasource.remote.datasource.UserDataSource
import com.sopt.dive.data.datasource.remote.datasourceimpl.AuthDataSourceImpl
import com.sopt.dive.data.datasource.remote.datasourceimpl.ReqresDataSourceImpl
import com.sopt.dive.data.datasource.remote.datasourceimpl.UserDataSourceImpl
import com.sopt.dive.domain.repository.AuthRepository
import com.sopt.dive.data.repositoryimpl.AuthRepositoryImpl
import com.sopt.dive.domain.repository.ReqresRepository
import com.sopt.dive.data.repositoryimpl.ReqresRepositoryImpl
import com.sopt.dive.domain.repository.UserRepository
import com.sopt.dive.data.repositoryimpl.UserRepositoryImpl
import com.sopt.dive.data.service.AuthService
import com.sopt.dive.data.service.ReqresService
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

    private val reqresService: ReqresService by lazy {
        ApiFactory.createReqres<ReqresService>()
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

    private val reqresDataSource: ReqresDataSource by lazy {
        ReqresDataSourceImpl(reqresService)
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

    val reqresRepository: ReqresRepository by lazy {
        ReqresRepositoryImpl(
            dataStoreDataSource = dataStoreDataSource,
            reqresDataSource = reqresDataSource
        )
    }
}