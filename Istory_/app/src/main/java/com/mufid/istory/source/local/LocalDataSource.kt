package com.mufid.istory.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.mufid.istory.source.local.datastore.LoginPreference

class LocalDataSource(private val loginPreference: LoginPreference) {

    suspend fun saveUserToken(token: String) =
        loginPreference.saveUserToken(token)

    fun getUserToken(): LiveData<String> =
        loginPreference.getUserToken().asLiveData()

    suspend fun saveFirstTime(isFirsTime: Boolean) =
        loginPreference.saveFirstTime(isFirsTime)

    fun getFirstTime(): LiveData<Boolean> =
        loginPreference.getFirstTime().asLiveData()

    suspend fun clearCache() = loginPreference.clearCache()

    companion object {
        @Volatile
        private var instance: LocalDataSource? = null
        fun getInstance(loginPreference: LoginPreference): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(loginPreference)
            }
    }
}