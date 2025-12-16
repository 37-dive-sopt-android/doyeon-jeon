package com.sopt.dive.data.local.datasource

import com.sopt.dive.data.local.model.LocalAccountModel
import com.sopt.dive.data.local.model.LocalUserModel

interface DataStoreDataSource {
    suspend fun getUserInfo(): LocalUserModel?
    suspend fun getAccountInfo(): LocalAccountModel?
    suspend fun setAccountInfo(id: String, pw: String)
    suspend fun setUserInfo(id: String, nickname: String, email: String, age: Int, status: String)
    suspend fun setLoginStatus(status: Boolean)
    suspend fun getProfileImages(): List<String>?
    suspend fun setProfileImages(images: List<String>)
}