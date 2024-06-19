package com.mufid.istory.source.remote.response.user

import com.google.gson.annotations.SerializedName

data class LoginResult(

    @field:SerializedName("token")
    val token: String
)