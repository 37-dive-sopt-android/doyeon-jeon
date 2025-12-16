package com.sopt.dive.data.datasource.remote.datasource.auth

import com.sopt.dive.core.model.BaseResponse
import com.sopt.dive.data.service.dto.request.LoginRequestDto
import com.sopt.dive.data.service.dto.response.LoginResponseDto

interface AuthDataSource {

    suspend fun login(
        body: LoginRequestDto,
    ): BaseResponse<LoginResponseDto>

}