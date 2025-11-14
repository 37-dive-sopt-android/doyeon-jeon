package com.sopt.dive.data.repository.auth

import com.sopt.dive.core.manager.AuthManager
import com.sopt.dive.core.util.suspendRunCatching
import com.sopt.dive.data.datasource.remote.auth.AuthDataSource
import com.sopt.dive.data.service.dto.request.LoginRequestDto

class AuthRepositoryImpl(
    private val authDataSource: AuthDataSource,
) : AuthRepository {

    override suspend fun login(
        id: String,
        password: String,
    ): Result<Int> {
        val result = suspendRunCatching {
            authDataSource.login(
                body = LoginRequestDto(
                    username = id,
                    password = password
                )
            ).data.userId
        }
        result.onSuccess { userId ->
            AuthManager.setUserId(userId)
        }
        return result
    }

}