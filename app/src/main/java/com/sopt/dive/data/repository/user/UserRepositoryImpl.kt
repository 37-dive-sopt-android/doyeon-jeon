package com.sopt.dive.data.repository.user

import com.sopt.dive.core.util.suspendRunCatching
import com.sopt.dive.data.datasource.remote.user.UserDataSource
import com.sopt.dive.data.repository.user.model.UserModel
import com.sopt.dive.data.repository.user.model.toModel
import com.sopt.dive.data.service.dto.request.SignUpRequestDto

class UserRepositoryImpl(
    private val userDataSource: UserDataSource,
) : UserRepository {

    override suspend fun getUserInfo(userId: Int): Result<UserModel> =
        suspendRunCatching {
            userDataSource.getUserInfo(userId).data.toModel()
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
