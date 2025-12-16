package com.sopt.dive.di.feature

import com.sopt.dive.core.network.ApiFactory
import com.sopt.dive.data.remote.datasource.auth.AuthDataSource
import com.sopt.dive.data.remote.datasourceimpl.auth.AuthDataSourceImpl
import com.sopt.dive.data.remote.service.AuthService
import com.sopt.dive.data.repositoryimpl.auth.AuthRepositoryImpl
import com.sopt.dive.di.core.DataStoreModule
import com.sopt.dive.domain.repository.AuthRepository

object AuthModule {
    private val authService: AuthService by lazy {
        ApiFactory.create<AuthService>()
    }

    private val authDataSource: AuthDataSource by lazy {
        AuthDataSourceImpl(authService)
    }

    val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(
            dataStoreDataSource = DataStoreModule.dataStoreDataSource,
            authDataSource = authDataSource,
        )
    }
}