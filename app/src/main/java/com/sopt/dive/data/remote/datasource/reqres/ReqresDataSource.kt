package com.sopt.dive.data.remote.datasource.reqres

import com.sopt.dive.data.remote.service.dto.response.ReqresUserResponse

interface ReqresDataSource {

    suspend fun getUserList(
        page: Int,
        perPage: Int,
    ): ReqresUserResponse

}