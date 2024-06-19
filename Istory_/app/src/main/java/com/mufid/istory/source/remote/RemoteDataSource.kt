package com.mufid.istory.source.remote

import android.util.Log
import com.mufid.istory.source.remote.response.api.ApiConfig
import com.mufid.istory.source.remote.response.story.StoryResponse
import com.mufid.istory.source.remote.response.upload.UploadResponse
import com.mufid.istory.source.remote.response.user.LoginResponse
import com.mufid.istory.source.remote.response.user.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {
    fun login(email: String, password: String, callback: LoginCallback) {
        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                callback.onLoginCallback(response.body())
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("FAILURE", "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun register(name: String, email: String, password: String, callback: RegisterCallback) {
        val client = ApiConfig.getApiService().register(name, email, password)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    callback.onRegisterCallback(response.body())
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.d("REGISTER", "onFailure: ${t.message}")
            }
        })
    }

    fun getAllStory(token: String, callback: AllStoryCallback){
        val client = ApiConfig.getApiService().getAllStory(token)
        client.enqueue(object : Callback<StoryResponse>{
            override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
                callback.onAllStoryCallback(response.body())
                Log.d("STORY_SUCCESS", "onResponse: ${response.message()}, ${response.body()?.listStory}")
            }

            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                Log.d("STORY", "onFailure: ${t.message}")
            }
        })
    }

    fun uploadStory(token: String, photo: MultipartBody.Part, desc: RequestBody, callback: UploadCallback) {
        val client = ApiConfig.getApiService().uploadStory(token, photo, desc)
        client.enqueue(object : Callback<UploadResponse> {
            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                Log.d("UPLOAD_TES", "onResponse: ${response.body()}, ${response.message()}")
                callback.onUploadCallback(response.body())
            }

            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                Log.d("UPLOAD_TES_FAIL", "onFailure: ${t.message.toString()}")
            }
        })
    }

    interface LoginCallback {
        fun onLoginCallback(loginResponse: LoginResponse?)
    }

    interface RegisterCallback {
        fun onRegisterCallback(registerResponse: RegisterResponse?)
    }

    interface AllStoryCallback{
        fun onAllStoryCallback(storyResponse: StoryResponse?)
    }

    interface UploadCallback {
        fun onUploadCallback(uploadResponse: UploadResponse?)
    }

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null
        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }
}