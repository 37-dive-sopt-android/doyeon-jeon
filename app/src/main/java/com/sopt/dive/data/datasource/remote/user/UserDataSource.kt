package com.sopt.dive.data.datasource.remote.user

import com.sopt.dive.core.model.BaseResponse
import com.sopt.dive.data.service.dto.request.SignUpRequestDto
import com.sopt.dive.data.service.dto.response.MemberResponseDto

interface UserDataSource {

    suspend fun getUserInfo(
        userId: Int,
    ): BaseResponse<MemberResponseDto>

    suspend fun signUp(
        body: SignUpRequestDto,
    ): BaseResponse<MemberResponseDto>

}