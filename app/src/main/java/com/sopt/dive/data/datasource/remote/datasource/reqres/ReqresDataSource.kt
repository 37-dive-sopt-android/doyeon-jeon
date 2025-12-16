package com.sopt.dive.data.datasource.remote.datasource.reqres

import com.sopt.dive.data.service.dto.response.ReqresUserResponse

interface ReqresDataSource {

    suspend fun getUserList(
        page: Int,
        perPage: Int,
    ): ReqresUserResponse

}