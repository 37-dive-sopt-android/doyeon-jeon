package com.sopt.dive.data.remote.datasourceimpl.reqres

import com.sopt.dive.data.remote.datasource.reqres.ReqresDataSource
import com.sopt.dive.data.remote.service.ReqresService
import com.sopt.dive.data.remote.service.dto.response.ReqresUserResponse

class ReqresDataSourceImpl(
    private val reqresService: ReqresService,
) : ReqresDataSource {

    override suspend fun getUserList(
        page: Int,
        perPage: Int,
    ): ReqresUserResponse = reqresService.getUserList(page, perPage)

}