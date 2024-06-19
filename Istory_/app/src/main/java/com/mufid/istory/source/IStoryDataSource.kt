package com.mufid.istory.source

import androidx.lifecycle.LiveData
import com.mufid.istory.source.remote.response.story.StoryResponse
import com.mufid.istory.source.remote.response.upload.UploadResponse
import com.mufid.istory.source.remote.response.user.LoginResponse
import com.mufid.istory.source.remote.response.user.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface IStoryDataSource {

    fun login(email: String, password: String): LiveData<LoginResponse>

    fun register(name: String, email: String, password: String): LiveData<RegisterResponse>

    fun getAllStory(token: String): LiveData<StoryResponse>

    suspend fun saveUserToken(token: String)

    fun getUserToken(): LiveData<String>

    suspend fun saveIsFirstTime(isFirstTime: Boolean)

    fun getIsFirstTime(): LiveData<Boolean>

    suspend fun clearCache()

    fun uploadStory(token: String, photo: MultipartBody.Part, desc: RequestBody): LiveData<UploadResponse>
}