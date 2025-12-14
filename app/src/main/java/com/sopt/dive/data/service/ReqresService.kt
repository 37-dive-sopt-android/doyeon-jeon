package com.sopt.dive.data.service

import com.sopt.dive.data.service.dto.response.ReqresUserResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ReqresService {

    @GET("/api/users")
    suspend fun getUserList(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Header("x-api-key") apiKey: String = "reqres-free-v1",
    ): ReqresUserResponse

}