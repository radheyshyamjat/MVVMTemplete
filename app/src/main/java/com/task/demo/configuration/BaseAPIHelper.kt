package com.task.demo.configuration


import com.task.demo.data.retrofit.APIService
import com.task.demo.data.retrofit.RetrofitClient
import com.task.demo.utils.debug
import com.task.demo.BuildConfig
import com.task.demo.utils.ErrorParseMessageUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class BaseAPIHelper {

    protected lateinit var app: App
    protected lateinit var apiService: APIService

    protected val serverError: String
        @Synchronized get() = "We apologize for the inconvenience. Please try again later"

    interface OnRequestComplete<T> {
        fun onSuccess(any: T)

        fun onFailure(errorMessage: String, errorCode: Int)
    }

    fun setApplication(application: App) {
        this.app = application
        this.apiService = RetrofitClient.getClient(BuildConfig.BASE_URL).create(APIService::class.java)
    }

    protected abstract inner class ResponseHandler<T> : Callback<T> {

        private var mOnRequestComplete: OnRequestComplete<*>
        private var mLogTag = "ResponseHandler"

        override fun onResponse(call: Call<T>, response: Response<T>) {
                try {
                    when (response.code()) {
                        RESULT_SUCCESS -> onSuccess(response)
                        RESULT_SUCCESS_CREATED -> onSuccess(response)
                        else -> onFailure(response)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    onException(e)
                    onFailure()
                }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            onRetrofitFailure(t)
        }

        internal constructor(onRequestComplete: OnRequestComplete<*>) {
            mOnRequestComplete = onRequestComplete
        }

        internal constructor(onRequestComplete: OnRequestComplete<*>, logTag: String) {
            mOnRequestComplete = onRequestComplete
            mLogTag = logTag
        }

        @Throws(Exception::class)
        abstract fun onSuccess(response: Response<T>)

        private fun onException(e: Exception) {
            debug("BaseAPIHelper : " + mLogTag + " : onException(): " + e.message)
            e.printStackTrace()
            mOnRequestComplete.onFailure(serverError, TEMP_CODE)
        }

        fun onFailure(response: Response<*>) {
            val errorMessage: String
            errorMessage = try {
                ErrorParseMessageUtils.getMessageFromErrorBody(response.errorBody()!!.string())
            } catch (e: Exception) {
                e.message!!
            }

            debug("BaseAPIHelper : $mLogTag :  onFailure(): $errorMessage")
            mOnRequestComplete.onFailure(errorMessage, response.code())
        }

        fun onFailure() {
            debug("BaseAPIHelper : $mLogTag :  onFailure(): Getting null response from the server")
            mOnRequestComplete.onFailure(serverError, TEMP_CODE)
        }

        fun onFailure(errorMsg: String) {
            debug("BaseAPIHelper : $mLogTag :  onFailure(): Server custom error")
            mOnRequestComplete.onFailure(errorMsg, TEMP_CODE)
        }

        private fun onRetrofitFailure(t: Throwable?) {
            if (null != t) {
                debug("BaseAPIHelper : " + mLogTag + " : onRetrofitFailure(): " + t.message)
            } else {
                debug("BaseAPIHelper : $mLogTag :  onRetrofitFailure(): Throwable Object is null")
            }
            mOnRequestComplete.onFailure(serverError, TEMP_CODE)
        }
    }

    companion object {

        // Response Code
        public const val RESULT_SUCCESS = 200
        public const val RESULT_SUCCESS_CREATED = 201
        private const val TEMP_CODE = -8
    }
}
