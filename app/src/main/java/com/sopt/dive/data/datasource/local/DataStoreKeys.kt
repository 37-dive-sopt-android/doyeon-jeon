package com.sopt.dive.data.datasource.local

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey

object DataStoreKeys {
    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    val ID = stringPreferencesKey("user_id")
    val PW = stringPreferencesKey("user_pw")
    val NICKNAME = stringPreferencesKey("user_nickname")
    val EMAIL = stringPreferencesKey("user_email")
    val AGE = intPreferencesKey("user_age")
    val STATUS = stringPreferencesKey("user_status")
    val PROFILE_IMAGES = stringSetPreferencesKey("profile_images")
}
