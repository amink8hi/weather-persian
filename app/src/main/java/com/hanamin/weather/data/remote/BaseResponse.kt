package com.hanamin.weather.data.remote

import com.google.gson.annotations.SerializedName

open class BaseResponse {

    @SerializedName("Response")
    var responses = ""

    @SerializedName("Error")
    var error = ""

    override fun toString(): String {
        return "RegisterResponse{" +
                "response = '" + responses + '\''.toString() +
                ",error = '" + error + '\''.toString() +
                "}"
    }
}
