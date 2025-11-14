package com.sopt.dive.data.repository.user

import com.sopt.dive.core.manager.AuthManager
import com.sopt.dive.core.util.suspendRunCatching
import com.sopt.dive.data.datasource.local.DataStoreDataSource
import com.sopt.dive.data.datasource.remote.user.UserDataSource
import com.sopt.dive.data.repository.user.model.UserModel
import com.sopt.dive.data.repository.user.model.toModel
import com.sopt.dive.data.service.dto.request.SignUpRequestDto

class UserRepositoryImpl(
    private val dataStoreDataSource: DataStoreDataSource,
    private val userDataSource: UserDataSource,
) : UserRepository {

    override suspend fun getUserInfo(userId: Int): Result<UserModel> =
        suspendRunCatching {
            userDataSource.getUserInfo(userId).data.toModel()
        }

    override suspend fun getMyInfo(): Result<UserModel> {
        // 로컬 데이터 확인
        val localInfo = dataStoreDataSource.getUserInfo()

        return if (localInfo != null) {
            Result.success(localInfo.toModel())
        } else {
            // 로컬 데이터 없을 때만 API 호출
            val result = suspendRunCatching {
                userDataSource.getUserInfo(AuthManager.userId!!).data.toModel()
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
        suspendRunCatching {
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
