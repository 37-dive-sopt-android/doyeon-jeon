package com.sopt.dive.data.datasource.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.sopt.dive.data.datasource.local.model.LocalAccountModel
import com.sopt.dive.data.datasource.local.model.LocalUserModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class DataStoreDataSourceImpl(
    private val dataStore: DataStore<Preferences>,
) : DataStoreDataSource {
    override suspend fun getUserInfo(): LocalUserModel? = dataStore.data.map { preferences ->
        val id = preferences[DataStoreKeys.ID]
        val nickname = preferences[DataStoreKeys.NICKNAME]
        val email = preferences[DataStoreKeys.EMAIL]
        val age = preferences[DataStoreKeys.AGE]
        val status = preferences[DataStoreKeys.STATUS]

        if (id == null || nickname == null || email == null || age == null || status == null) {
            null
        } else {
            LocalUserModel(id, nickname, email, age, status)
        }
    }.firstOrNull()

    override suspend fun getAccountInfo(): LocalAccountModel? = dataStore.data.map { preferences ->
        val id = preferences[DataStoreKeys.ID]
        val pw = preferences[DataStoreKeys.PW]

        if (id == null || pw == null) {
            null
        } else {
            LocalAccountModel(id, pw)
        }
    }.firstOrNull()

    override suspend fun setAccountInfo(id: String, pw: String) {
        dataStore.edit { user ->
            user[DataStoreKeys.ID] = id
            user[DataStoreKeys.PW] = pw
        }
    }

    override suspend fun setUserInfo(
        id: String,
        nickname: String,
        email: String,
        age: Int,
        status: String,
    ) {
        dataStore.edit { user ->
            user[DataStoreKeys.ID] = id
            user[DataStoreKeys.NICKNAME] = nickname
            user[DataStoreKeys.EMAIL] = email
            user[DataStoreKeys.AGE] = age
            user[DataStoreKeys.STATUS] = status
        }
    }

    override suspend fun setLoginStatus(status: Boolean) {
        dataStore.edit { user ->
            user[DataStoreKeys.IS_LOGGED_IN] = status
        }
    }

    override suspend fun getProfileImages(): List<String>? = dataStore.data.map { preferences ->
        preferences[DataStoreKeys.PROFILE_IMAGES]?.toList()
    }.firstOrNull()

    override suspend fun setProfileImages(images: List<String>) {
        dataStore.edit { user ->
            user[DataStoreKeys.PROFILE_IMAGES] = images.toSet()
        }
    }
}
