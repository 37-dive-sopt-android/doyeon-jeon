package com.sopt.dive.data.remote.datasourceimpl.auth

import com.sopt.dive.core.model.BaseResponse
import com.sopt.dive.data.remote.datasource.auth.AuthDataSource
import com.sopt.dive.data.remote.service.AuthService
import com.sopt.dive.data.remote.service.dto.request.LoginRequestDto
import com.sopt.dive.data.remote.service.dto.response.LoginResponseDto

class AuthDataSourceImpl(
    private val authService: AuthService,
) : AuthDataSource {

    override suspend fun login(body: LoginRequestDto): BaseResponse<LoginResponseDto> =
        authService.login(body)

}