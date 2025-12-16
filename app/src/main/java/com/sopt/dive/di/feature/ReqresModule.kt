package com.sopt.dive.di.feature

import com.sopt.dive.core.network.ApiFactory
import com.sopt.dive.data.remote.datasource.reqres.ReqresDataSource
import com.sopt.dive.data.remote.datasourceimpl.reqres.ReqresDataSourceImpl
import com.sopt.dive.data.remote.service.ReqresService
import com.sopt.dive.data.repositoryimpl.reqres.ReqresRepositoryImpl
import com.sopt.dive.di.core.DataStoreModule
import com.sopt.dive.domain.repository.ReqresRepository

object ReqresModule {
    private val reqresService: ReqresService by lazy {
        ApiFactory.createReqres<ReqresService>()
    }

    private val reqresDataSource: ReqresDataSource by lazy {
        ReqresDataSourceImpl(
            reqresService = reqresService
        )
    }

    val reqresRepository: ReqresRepository by lazy {
        ReqresRepositoryImpl(
            dataStoreDataSource = DataStoreModule.dataStoreDataSource,
            reqresDataSource = reqresDataSource
        )
    }
}