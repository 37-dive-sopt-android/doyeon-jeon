package com.sopt.dive.data.remote.service

import com.sopt.dive.core.model.BaseResponse
import com.sopt.dive.data.remote.service.dto.request.SignUpRequestDto
import com.sopt.dive.data.remote.service.dto.response.MemberResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {

    @GET("/api/v1/users/{id}")
    suspend fun getUserInfo(
        @Path(value = "id") userId: Int,
    ): BaseResponse<MemberResponseDto>

    @POST("/api/v1/users")
    suspend fun signUp(
        @Body body: SignUpRequestDto,
    ): BaseResponse<MemberResponseDto>

}