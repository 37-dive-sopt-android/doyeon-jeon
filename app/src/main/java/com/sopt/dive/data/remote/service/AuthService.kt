package com.sopt.dive.data.remote.service

import com.sopt.dive.core.model.BaseResponse
import com.sopt.dive.data.remote.service.dto.request.LoginRequestDto
import com.sopt.dive.data.remote.service.dto.response.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/api/v1/auth/login")
    suspend fun login(
        @Body body: LoginRequestDto,
    ): BaseResponse<LoginResponseDto>

}