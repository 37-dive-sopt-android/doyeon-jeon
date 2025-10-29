package com.sopt.dive.data

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    val ID = stringPreferencesKey("user_id")
    val PW = stringPreferencesKey("user_pw")
    val NICKNAME = stringPreferencesKey("user_nickname")
    val MBTI = stringPreferencesKey("user_mbti")
}