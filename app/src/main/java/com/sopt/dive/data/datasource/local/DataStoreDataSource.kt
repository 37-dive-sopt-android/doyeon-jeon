package com.sopt.dive.data.datasource.local

import com.sopt.dive.data.model.AccountInfo
import com.sopt.dive.data.model.UserPrefs

interface DataStoreDataSource {
    suspend fun getUserInfo(): UserPrefs?
    suspend fun getAccountInfo(): AccountInfo?
    suspend fun setAccountInfo(id: String, pw: String)
    suspend fun setUserInfo(id: String, pw: String, nickname: String, mbti: String)
    suspend fun setLoginStatus(status: Boolean)
}
