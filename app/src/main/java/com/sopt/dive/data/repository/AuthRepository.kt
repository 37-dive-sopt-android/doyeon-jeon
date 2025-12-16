package com.sopt.dive.data.repository

interface AuthRepository {

    suspend fun login(
        id: String,
        password: String,
    ): Result<Int>

    suspend fun autoLogin(
    ): Result<Int>

}