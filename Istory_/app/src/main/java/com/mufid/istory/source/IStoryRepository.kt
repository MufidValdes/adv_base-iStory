package com.mufid.istory.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mufid.istory.source.local.LocalDataSource
import com.mufid.istory.source.remote.RemoteDataSource
import com.mufid.istory.source.remote.response.story.StoryResponse
import com.mufid.istory.source.remote.response.upload.UploadResponse
import com.mufid.istory.source.remote.response.user.LoginResponse
import com.mufid.istory.source.remote.response.user.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class IStoryRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): IStoryDataSource {

    override fun login(email: String, password: String): LiveData<LoginResponse> {
        val loginResult = MutableLiveData<LoginResponse>()
        remoteDataSource.login(email, password, object : RemoteDataSource.LoginCallback {
            override fun onLoginCallback(loginResponse: LoginResponse?) {
                loginResponse.let {
                    val response = LoginResponse(
                        it?.loginResult,
                        it?.error,
                        it?.message
                    )
                    loginResult.postValue(response)
                }
            }
        })
        return loginResult
    }

    override fun register(name: String, email: String, password: String): LiveData<RegisterResponse> {
        val registerResult = MutableLiveData<RegisterResponse>()
        remoteDataSource.register(name, email, password, object : RemoteDataSource.RegisterCallback {
            override fun onRegisterCallback(registerResponse: RegisterResponse?) {
                registerResponse?.let {
                    val response = RegisterResponse(
                        it.error,
                        it.message
                    )
                    registerResult.postValue(response)
                }
            }
        })
        return registerResult
    }

    override fun getAllStory(token: String): LiveData<StoryResponse> {
        val getAllStoryResult = MutableLiveData<StoryResponse>()
        remoteDataSource.getAllStory(token, object : RemoteDataSource.AllStoryCallback{
            override fun onAllStoryCallback(storyResponse: StoryResponse?) {
                storyResponse?.let {
                    val response = StoryResponse(
                        it.listStory,
                        it.error,
                        it.message
                    )
                    getAllStoryResult.postValue(response)
                }
            }
        })
        return getAllStoryResult
    }

    override suspend fun saveUserToken(token: String) {
        localDataSource.saveUserToken(token)
    }

    override fun getUserToken(): LiveData<String> =
        localDataSource.getUserToken()

    override suspend fun saveIsFirstTime(isFirstTime: Boolean) {
        localDataSource.saveFirstTime(isFirstTime)
    }

    override fun uploadStory(
        token: String,
        photo: MultipartBody.Part,
        desc: RequestBody
    ): LiveData<UploadResponse> {
        val uploadResult = MutableLiveData<UploadResponse>()
        remoteDataSource.uploadStory(token, photo, desc, object : RemoteDataSource.UploadCallback {
            override fun onUploadCallback(uploadResponse: UploadResponse?) {
                uploadResponse?.let {
                    uploadResult.postValue(it)
                }
            }
        })
        return uploadResult
    }

    override fun getIsFirstTime(): LiveData<Boolean> =
        localDataSource.getFirstTime()

    override suspend fun clearCache() {
        localDataSource.clearCache()
    }

    companion object {
        @Volatile
        private var instance: IStoryRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource): IStoryRepository =
            instance ?: synchronized(this) {
                instance ?: IStoryRepository(remoteDataSource, localDataSource).apply { instance = this }
            }
    }
}