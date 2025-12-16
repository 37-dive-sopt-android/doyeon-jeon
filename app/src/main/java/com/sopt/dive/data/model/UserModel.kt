package com.sopt.dive.data.model

import com.sopt.dive.data.local.model.LocalUserModel
import com.sopt.dive.data.remote.service.dto.response.MemberResponseDto

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
