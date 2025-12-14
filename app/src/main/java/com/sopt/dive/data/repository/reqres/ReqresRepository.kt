package com.sopt.dive.data.repository.reqres

interface ReqresRepository {

    suspend fun getUserList(
        page: Int = 1,
        perPage: Int = 30
    ): Result<List<String>>

}