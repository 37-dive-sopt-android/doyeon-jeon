package com.sopt.dive.data.repository.user.model

import com.sopt.dive.data.service.dto.response.MemberResponseDto

data class UserModel(
    val id: Int,
    val username: String,
    val name: String,
    val email: String,
    val age: Int,
    val status: String,
)

fun MemberResponseDto.toModel(): UserModel =
    UserModel(
        id = this.id,
        username = this.username,
        name = this.name,
        email = this.email,
        age = this.age,
        status = this.status
    )
