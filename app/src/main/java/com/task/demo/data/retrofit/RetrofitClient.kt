package com.task.demo.data.retrofit

import com.google.gson.GsonBuilder
import com.task.demo.configuration.App
import com.task.demo.utils.debug
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitClient {
    companion object {
        fun getClient(baseUrl: String): Retrofit {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(object : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val original = chain.request()
                        return if (App.getPreferences().getUserToken() == null) {
                            debug("user token is null")
                            val request = original.newBuilder()
                                .method(original.method, original.body)
                                .build()
                            chain.proceed(request)
                        } else {
                            debug("User token is not null")
                            val request2 = original.newBuilder()
                                .addHeader(
                                    "Authorization",
                                    "" + App.getPreferences().getUserToken()
                                )
                                .method(original.method, original.body)
                                .build()
                            chain.proceed(request2)
                        }
                    }
                })
                .addInterceptor(interceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
                .client(client)
                .build()
        }
    }
}