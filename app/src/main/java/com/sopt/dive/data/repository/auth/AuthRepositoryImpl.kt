package com.sopt.dive.data.repository.auth

import com.sopt.dive.core.manager.AuthManager
import com.sopt.dive.core.util.suspendRunCatching
import com.sopt.dive.data.datasource.local.DataStoreDataSource
import com.sopt.dive.data.datasource.remote.auth.AuthDataSource
import com.sopt.dive.data.service.dto.request.LoginRequestDto

class AuthRepositoryImpl(
    private val authDataSource: AuthDataSource,
    private val dataStoreDataSource: DataStoreDataSource,
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
            // 로그인 성공 시 로컬에 id/pw 저장
            dataStoreDataSource.setAccountInfo(id, password)
        }
        return result
    }

    // dataSource에서 꺼내서 위의 login 호출하기
    override suspend fun autoLogin(): Result<Int> {
        val localAccountInfo = dataStoreDataSource.getAccountInfo()
        return if (localAccountInfo != null && localAccountInfo.id != null && localAccountInfo.pw != null) {
            login(localAccountInfo.id, localAccountInfo.pw)
        } else {
            Result.failure(Exception())
        }
    }

}