package com.sopt.dive.data.mapper

import com.sopt.dive.data.local.model.LocalUserModel
import com.sopt.dive.data.model.User
import com.sopt.dive.data.remote.service.dto.response.MemberResponseDto

fun MemberResponseDto.toModel(): User =
    User(
        id = this.username,
        nickname = this.name,
        email = this.email,
        age = this.age,
        status = this.status
    )

fun LocalUserModel.toModel(): User =
    User(
        id = this.id ?: "",
        nickname = this.nickname ?: "",
        email = this.email ?: "",
        age = this.age ?: 0,
        status = this.status ?: ""
    )
