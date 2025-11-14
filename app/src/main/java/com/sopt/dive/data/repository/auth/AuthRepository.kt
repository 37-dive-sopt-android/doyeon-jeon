package com.sopt.dive.data.repository.auth

interface AuthRepository {

    suspend fun login(
        id: String,
        password: String,
    ): Result<Int>

}
