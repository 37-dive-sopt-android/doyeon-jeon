package com.sopt.dive.data.repository

import com.sopt.dive.data.model.User

interface UserRepository {

    suspend fun getUserInfo(
        userId: Int,
    ): Result<User>

    suspend fun getMyInfo(
    ): Result<User>

    suspend fun signUp(
        id: String,
        password: String,
        name: String,
        email: String,
        age: Int,
    ): Result<Unit>

}