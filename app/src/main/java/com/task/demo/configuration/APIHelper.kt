package com.task.demo.configuration

import com.task.demo.data.model.MasterWrapper
import com.task.demo.R
import org.json.JSONObject
import retrofit2.Response


class APIHelper : BaseAPIHelper() {

    private fun getString(stringId: Int): String {
        return App.getInstance().getString(stringId)
    }

    private fun showGeneralizedError(): String {
        return getString(R.string.response_msg_common)
    }

//    fun getPackageInfo(onRequestComplete: OnRequestComplete<WrapperPackageInfo>) {
//        apiService.getSubscribePackageInfo()
//            .enqueue(object : BaseAPIHelper.ResponseHandler<WrapperPackageInfo>(onRequestComplete) {
//                @Throws(Exception::class)
//                override fun onSuccess(response: Response<WrapperPackageInfo>) {
//                    if (response.code() == RESULT_OK)
//                        onRequestComplete.onSuccess(response.body()!!)
//                    else
//                        onRequestComplete.onFailure(showGeneralizedError(), response.code())
//                }
//            })
//    }

//    fun fcmToken(
//        deviceId: String, fcmToken: String,
//        onRequestComplete: OnRequestComplete<UserResponseWrapper>
//    ) {
//        apiService.fcmToken(deviceId, fcmToken)
//            .enqueue(object :
//                BaseAPIHelper.ResponseHandler<UserResponseWrapper>(onRequestComplete) {
//                @Throws(Exception::class)
//                override fun onSuccess(response: Response<UserResponseWrapper>) {
//                    if (response.code() == RESULT_OK)
//                        onRequestComplete.onSuccess(response.body()!!)
//                    else
//                        onRequestComplete.onFailure(showGeneralizedError(), response.code())
//                }
//            })
//    }


//    fun signUp(userDetail: UserDto, onRequestComplete: OnRequestComplete<MasterWrapper>) {
//        apiService.signUp(userDetail)
//            .enqueue(object : ResponseHandler<MasterWrapper>(onRequestComplete) {
//                @Throws(Exception::class)
//                override fun onSuccess(response: Response<MasterWrapper>) {
//                    if (response.code() == RESULT_CREATED)
//                        onRequestComplete.onSuccess(response.body()!!)
//                    else
//                        onRequestComplete.onFailure(showGeneralizedError(), response.code())
//                }
//            })
//    }

    fun checkEmail(email: String, onRequestComplete: OnRequestComplete<JSONObject>) {
        apiService.checkEmail(email)
            .enqueue(object : ResponseHandler<JSONObject>(onRequestComplete) {
                @Throws(Exception::class)
                override fun onSuccess(response: Response<JSONObject>) {
                    if (response.code() == RESULT_OK)
                        onRequestComplete.onSuccess(response.body()!!)
                    else
                        onRequestComplete.onFailure(showGeneralizedError(), response.code())
                }
            })
    }


//    fun getProfile(onRequestComplete: OnRequestComplete<ProfileWrapper>) {
//        apiService.getProfile()
//            .enqueue(object : BaseAPIHelper.ResponseHandler<ProfileWrapper>(onRequestComplete) {
//                @Throws(Exception::class)
//                override fun onSuccess(response: Response<ProfileWrapper>) {
//                    if (response.code() == RESULT_OK)
//                        onRequestComplete.onSuccess(response.body()!!)
//                    else
//                        onRequestComplete.onFailure(showGeneralizedError(), response.code())
//                }
//            })
//    }


    companion object {
        private const val RESULT_OK = 200
        private const val RESULT_CREATED = 201
        private const val HTTP_UNAUTHORIZED = 401
        private lateinit var instance: APIHelper

        fun refreshBase() {
            resetField(this, "instance")
        }

        @Synchronized
        fun init(app: App): APIHelper {
            return if (Companion::instance.isInitialized) {
                instance
            } else {
                instance = APIHelper()
                instance.setApplication(app)
                instance
            }
        }
    }
}

fun resetField(target: Any, fieldName: String) {
    val field = target.javaClass.getDeclaredField(fieldName)
    with(field) {
        isAccessible = true
        set(target, null)
    }
}
