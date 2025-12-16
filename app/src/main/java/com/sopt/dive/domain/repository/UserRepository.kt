package com.sopt.dive.domain.repository

import com.sopt.dive.domain.model.UserModel

interface UserRepository {

    suspend fun getUserInfo(
        userId: Int,
    ): Result<UserModel>

    suspend fun getMyInfo(
    ): Result<UserModel>

    suspend fun signUp(
        id: String,
        password: String,
        name: String,
        email: String,
        age: Int,
    ): Result<Unit>

}