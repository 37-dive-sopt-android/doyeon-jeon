package com.sopt.dive.domain.model

import com.sopt.dive.data.datasource.local.model.LocalUserModel
import com.sopt.dive.data.service.dto.response.MemberResponseDto

data class UserModel(
    val id: String,
    val nickname: String,
    val email: String,
    val age: Int,
    val status: String,
)

fun MemberResponseDto.toModel(): UserModel =
    UserModel(
        id = this.username,
        nickname = this.name,
        email = this.email,
        age = this.age,
        status = this.status
    )

fun LocalUserModel.toModel(): UserModel =
    UserModel(
        id = this.id ?: "",
        nickname = this.nickname ?: "",
        email = this.email ?: "",
        age = this.age ?: 0,
        status = this.status ?: ""
    )
