package com.sopt.dive.data.repositoryimpl.user

import com.sopt.dive.core.manager.AuthManager
import com.sopt.dive.core.util.apiRunCatching
import com.sopt.dive.core.util.getServerError
import com.sopt.dive.data.local.datasource.DataStoreDataSource
import com.sopt.dive.data.mapper.toModel
import com.sopt.dive.data.remote.datasource.user.UserDataSource
import com.sopt.dive.data.remote.service.dto.request.SignUpRequestDto
import com.sopt.dive.data.model.User
import com.sopt.dive.data.repository.UserRepository
import com.sopt.dive.data.type.AuthError
import com.sopt.dive.data.type.CommonError
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException

class UserRepositoryImpl(
    private val dataStoreDataSource: DataStoreDataSource,
    private val userDataSource: UserDataSource,
) : UserRepository {

    override suspend fun getUserInfo(userId: Int): Result<User> =
        apiRunCatching {
            userDataSource.getUserInfo(userId).data.toModel()
        }

    override suspend fun getMyInfo(): Result<User> {
        // 로컬 데이터 확인
        val localInfo = dataStoreDataSource.getUserInfo()

        return if (localInfo != null) {
            Result.success(localInfo.toModel())
        } else {
            val myId = AuthManager.userId ?: return Result.failure(AuthError.TokenExpired())

            // 로컬 데이터 없을 때만 API 호출
            val result = apiRunCatching {
                userDataSource.getUserInfo(myId).data.toModel()
            }
            // 성공 후 로컬에 저장
            result.onSuccess { userInfo ->
                dataStoreDataSource.setUserInfo(
                    userInfo.id,
                    userInfo.nickname,
                    userInfo.email,
                    userInfo.age,
                    userInfo.status
                )
            }
        }
    }

    override suspend fun signUp(
        id: String,
        password: String,
        name: String,
        email: String,
        age: Int,
    ): Result<Unit> {
        val result = apiRunCatching {
            userDataSource.signUp(
                body = SignUpRequestDto(
                    username = id,
                    password = password,
                    name = name,
                    email = email,
                    age = age
                )
            )
        }.map { }
        result.onFailure { e ->
            val exception = when (e) {
                is TimeoutCancellationException -> CommonError.Timeout()
                is HttpException -> {
                    when (val errorData = getServerError(e)) {
                        null -> CommonError.Unknown()
                        else -> when (errorData.code) {
                            "COMMON-409" -> AuthError.IdDuplicated()
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
}