package com.mufid.istory.source.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LoginPreference private constructor(private val dataStore: DataStore<Preferences>) {
    private val TOKENKEY = stringPreferencesKey("user_token")
    private val FIRSTKEY = booleanPreferencesKey("first_time")

    suspend fun saveUserToken(token: String) =
        dataStore.edit {
            it[TOKENKEY] = token
        }

    fun getUserToken(): Flow<String> =
        dataStore.data.map {
            it[TOKENKEY] ?: DEFAULT_VALUE
        }

    suspend fun clearCache() =
        dataStore.edit {
            it.clear()
        }

    suspend fun saveFirstTime(isFirstTime: Boolean) =
        dataStore.edit {
            it[FIRSTKEY] = isFirstTime
        }

    fun getFirstTime(): Flow<Boolean> =
        dataStore.data.map {
            it[FIRSTKEY] ?: true
        }

    companion object {
        @Volatile
        private var instance: LoginPreference? = null
        const val DEFAULT_VALUE = "no_token"

        fun getInstance(dataStore: DataStore<Preferences>): LoginPreference =
            instance ?: synchronized(this) {
                instance ?: LoginPreference(dataStore).apply {
                    instance = this
                }
            }
    }
}