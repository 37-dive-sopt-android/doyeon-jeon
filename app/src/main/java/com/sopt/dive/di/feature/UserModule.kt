package com.sopt.dive.di.feature

import com.sopt.dive.core.network.ApiFactory
import com.sopt.dive.data.remote.datasource.user.UserDataSource
import com.sopt.dive.data.remote.datasourceimpl.user.UserDataSourceImpl
import com.sopt.dive.data.remote.service.UserService
import com.sopt.dive.data.repositoryimpl.user.UserRepositoryImpl
import com.sopt.dive.di.core.DataStoreModule
import com.sopt.dive.domain.repository.UserRepository

object UserModule {
    private val userService: UserService by lazy {
        ApiFactory.create<UserService>()
    }

    private val userDataSource: UserDataSource by lazy {
        UserDataSourceImpl(
            userService = userService
        )
    }

    val userRepository: UserRepository by lazy {
        UserRepositoryImpl(
            dataStoreDataSource = DataStoreModule.dataStoreDataSource,
            userDataSource = userDataSource
        )
    }
}