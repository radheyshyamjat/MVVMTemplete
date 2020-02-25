package com.task.demo.data.repositries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.task.demo.configuration.App
import com.task.demo.configuration.BaseAPIHelper
import com.task.demo.data.model.MasterWrapper
import org.json.JSONObject

class AuthenticateRepository {
    private val registerLiveData = MutableLiveData<MasterWrapper>()
    private val checkEmailLiveData = MutableLiveData<JSONObject>()

    companion object {
        private lateinit var instance: AuthenticateRepository
        fun getInstance(): AuthenticateRepository {
            if (!(Companion::instance.isInitialized)) instance = AuthenticateRepository(); return instance
        }
    }

//    fun signUp(userDetail: UserDto): LiveData<MasterWrapper> {
//        App.getAPIHelper()
//            .signUp(userDetail, object : BaseAPIHelper.OnRequestComplete<MasterWrapper> {
//                override fun onSuccess(any: MasterWrapper) {
//                    registerLiveData.postValue(any)
//                }
//
//                override fun onFailure(errorMessage: String, errorCode: Int) {
//                    val error =
//                        MasterWrapper(
//                            null,
//                            errorMessage,
//                            errorCode
//                        )
//                    registerLiveData.postValue(error)
//                }
//            })
//        return registerLiveData
//    }

    fun checkEmail(email: String): LiveData<JSONObject> {
        App.getAPIHelper()
            .checkEmail(email, object : BaseAPIHelper.OnRequestComplete<JSONObject> {
                override fun onSuccess(any: JSONObject) {
                    val error = JSONObject()
                    error.put("success", true)
                    error.put("code", 200)
                    checkEmailLiveData.postValue(any)
                }

                override fun onFailure(errorMessage: String, errorCode: Int) {
                    val error = JSONObject()
                    error.put("message", errorMessage)
                    error.put("success", false)
                    error.put("code", errorCode)
                    checkEmailLiveData.postValue(error)
                }
            })
        return checkEmailLiveData
    }
}