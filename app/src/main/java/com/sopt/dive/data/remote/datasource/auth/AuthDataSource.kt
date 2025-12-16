package com.sopt.dive.data.remote.datasource.auth

import com.sopt.dive.core.model.BaseResponse
import com.sopt.dive.data.remote.service.dto.request.LoginRequestDto
import com.sopt.dive.data.remote.service.dto.response.LoginResponseDto

interface AuthDataSource {

    suspend fun login(
        body: LoginRequestDto,
    ): BaseResponse<LoginResponseDto>

}