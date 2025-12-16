package com.sopt.dive.data.repositoryimpl.auth

import com.sopt.dive.core.manager.AuthManager
import com.sopt.dive.core.util.apiRunCatching
import com.sopt.dive.core.util.getServerError
import com.sopt.dive.data.local.datasource.DataStoreDataSource
import com.sopt.dive.data.remote.datasource.auth.AuthDataSource
import com.sopt.dive.data.remote.service.dto.request.LoginRequestDto
import com.sopt.dive.data.repository.AuthRepository
import com.sopt.dive.data.type.AuthError
import com.sopt.dive.data.type.CommonError
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val authDataSource: AuthDataSource,
    private val dataStoreDataSource: DataStoreDataSource,
) : AuthRepository {

    override suspend fun login(
        id: String,
        password: String,
    ): Result<Int> {
        val result = apiRunCatching {
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
        result.onFailure { e ->
            val exception = when (e) {
                is TimeoutCancellationException -> CommonError.Timeout()
                is HttpException -> {
                    when (val errorData = getServerError(e)) {
                        null -> CommonError.Unknown()
                        else -> when (errorData.code) {
                            "COMMON-400-VAL", "COMMON-401" -> AuthError.InvalidCredentials()
                            else -> CommonError.Undefined(errorData.message)
                        }
                    }
                }
                else -> CommonError.Unknown()
            }
            return Result.failure(exception)
        }
        return result
    }

    // dataSource에서 꺼내서 위의 login 호출하기
    override suspend fun autoLogin(): Result<Int> {
        val localAccountInfo = dataStoreDataSource.getAccountInfo()
        return if (localAccountInfo != null) {
            login(localAccountInfo.id!!, localAccountInfo.pw!!)
        } else {
            Result.failure(AuthError.TokenExpired())
        }
    }
}
