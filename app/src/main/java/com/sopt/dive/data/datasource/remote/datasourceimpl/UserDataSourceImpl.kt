package com.sopt.dive.data.datasource.remote.datasourceimpl

import com.sopt.dive.core.model.BaseResponse
import com.sopt.dive.data.datasource.remote.datasource.UserDataSource
import com.sopt.dive.data.service.UserService
import com.sopt.dive.data.service.dto.request.SignUpRequestDto
import com.sopt.dive.data.service.dto.response.MemberResponseDto

class UserDataSourceImpl(
    private val userService: UserService,
) : UserDataSource {

    override suspend fun getUserInfo(userId: Int): BaseResponse<MemberResponseDto> =
        userService.getUserInfo(userId)

    override suspend fun signUp(body: SignUpRequestDto): BaseResponse<MemberResponseDto> =
        userService.signUp(body)

}