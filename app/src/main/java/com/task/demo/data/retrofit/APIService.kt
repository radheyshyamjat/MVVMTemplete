package com.task.demo.data.retrofit

import com.task.demo.data.model.LoginUserDto
import com.task.demo.data.model.MasterWrapper
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface APIService {
//    @POST("signup/")
//    fun signUp(@Body userDto: UserDto): Call<MasterWrapper>

    @POST("login/")
    fun loginGoogle(@Body loginInfo: LoginUserDto): Call<MasterWrapper>

    @POST("user/check/")
    @FormUrlEncoded
    fun checkEmail(@Field("email") email: String): Call<JSONObject>

//    @GET("package/user/")
//    fun getHistory(): Call<WrapperPackageBuy>
//
//    @GET("package/")
//    fun getSubscribePackageInfo(): Call<WrapperPackageInfo>
}