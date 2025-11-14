package com.sopt.dive.data.datasource.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.sopt.dive.data.model.AccountInfo
import com.sopt.dive.data.model.UserPrefs
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class DataStoreDataSourceImpl(
    private val dataStore: DataStore<Preferences>,
) : DataStoreDataSource {
    override suspend fun getUserInfo(): UserPrefs? = dataStore.data.map { preferences ->
        UserPrefs(
            isLoggedIn = preferences[DataStoreKeys.IS_LOGGED_IN],
            id = preferences[DataStoreKeys.ID],
            pw = preferences[DataStoreKeys.PW],
            nickname = preferences[DataStoreKeys.NICKNAME],
            mbti = preferences[DataStoreKeys.MBTI]
        )
    }.firstOrNull()

    override suspend fun getAccountInfo(): AccountInfo? = dataStore.data.map { preferences ->
        AccountInfo(
            id = preferences[DataStoreKeys.ID],
            pw = preferences[DataStoreKeys.PW],
        )
    }.firstOrNull()

    override suspend fun setAccountInfo(id: String, pw: String) {
        dataStore.edit { user ->
            user[DataStoreKeys.ID] = id
            user[DataStoreKeys.PW] = pw
        }
    }

    override suspend fun setUserInfo(
        id: String,
        pw: String,
        name: String,
        mbti: String,
    ) {
        dataStore.edit { user ->
            user[DataStoreKeys.IS_LOGGED_IN] = false
            user[DataStoreKeys.ID] = id
            user[DataStoreKeys.PW] = pw
            user[DataStoreKeys.NICKNAME] = name
            user[DataStoreKeys.MBTI] = mbti
        }
    }

    override suspend fun setLoginStatus(status: Boolean) {
        dataStore.edit { user ->
            user[DataStoreKeys.IS_LOGGED_IN] = status
        }
    }
}
