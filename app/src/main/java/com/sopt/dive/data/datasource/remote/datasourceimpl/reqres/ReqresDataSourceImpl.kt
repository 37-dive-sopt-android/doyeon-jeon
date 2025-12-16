package com.sopt.dive.data.datasource.remote.datasourceimpl.reqres

import com.sopt.dive.data.datasource.remote.datasource.reqres.ReqresDataSource
import com.sopt.dive.data.service.ReqresService
import com.sopt.dive.data.service.dto.response.ReqresUserResponse

class ReqresDataSourceImpl(
    private val reqresService: ReqresService,
) : ReqresDataSource {

    override suspend fun getUserList(
        page: Int,
        perPage: Int,
    ): ReqresUserResponse = reqresService.getUserList(page, perPage)

}