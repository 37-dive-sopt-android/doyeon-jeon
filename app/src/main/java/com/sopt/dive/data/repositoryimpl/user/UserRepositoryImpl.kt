package com.sopt.dive.data.repositoryimpl.user

import com.sopt.dive.core.exception.UnauthorizedException
import com.sopt.dive.core.manager.AuthManager
import com.sopt.dive.core.util.apiRunCatching
import com.sopt.dive.data.datasource.local.datasource.DataStoreDataSource
import com.sopt.dive.data.datasource.remote.datasource.user.UserDataSource
import com.sopt.dive.data.service.dto.request.SignUpRequestDto
import com.sopt.dive.domain.model.UserModel
import com.sopt.dive.domain.model.toModel
import com.sopt.dive.domain.repository.UserRepository

class UserRepositoryImpl(
    private val dataStoreDataSource: DataStoreDataSource,
    private val userDataSource: UserDataSource,
) : UserRepository {

    override suspend fun getUserInfo(userId: Int): Result<UserModel> =
        apiRunCatching {
            userDataSource.getUserInfo(userId).data.toModel()
        }

    override suspend fun getMyInfo(): Result<UserModel> {
        // 로컬 데이터 확인
        val localInfo = dataStoreDataSource.getUserInfo()

        return if (localInfo != null) {
            Result.success(localInfo.toModel())
        } else {
            val myId = AuthManager.userId ?: return Result.failure(UnauthorizedException())

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
    ): Result<Unit> =
        apiRunCatching {
            userDataSource.signUp(
                body = SignUpRequestDto(
                    username = id,
                    password = password,
                    name = name,
                    email = email,
                    age = age
                )
            )
        }

}