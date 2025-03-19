package com.harman.androidvehicleconnectsdk.network.networkmanager
/********************************************************************************
 * Copyright (c) 2023-24 Harman International
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 ********************************************************************************/
import com.google.gson.Gson
import com.harman.androidvehicleconnectsdk.appauth.RefreshTokenAuthenticator
import com.harman.androidvehicleconnectsdk.helper.Constant
import com.harman.androidvehicleconnectsdk.helper.sharedpref.AppDataStorage
import com.harman.androidvehicleconnectsdk.network.EndPoint
import com.harman.androidvehicleconnectsdk.network.debugprint.DebugPrint
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * RetrofitProvider is a singleton class which have functions related
 * to create the retrofit client and loggers
 */
object RetrofitProvider {
    private const val TIME_OUT = 60L
    private const val TAG = "SDK_NETWORK_LAYER"
    var retryCount = 1

    /**
     * This function is used to create the retrofit client
     *
     * @param endPoint EndPoint interface which holds the necessary data to do retrofit call
     * @return Retrofit instance
     */
    fun getRetrofitClient(endPoint: EndPoint): Retrofit? {
        return endPoint.baseUrl?.let {
            Retrofit.Builder()
                .baseUrl(it)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .client(getOkHttpClient(endPoint))
                .build()
        }
    }

    /**
     * This function is used to create the OkHttpClient instance
     *
     * @param endPoint EndPoint interface which holds the necessary data to do retrofit call
     * @return OkHttpClient instance
     */
    private fun getOkHttpClient(endPoint: EndPoint): OkHttpClient {
        val builder: OkHttpClient.Builder =
            OkHttpClient.Builder()
                .authenticator(RefreshTokenAuthenticator())
                .addInterceptor(getHeadersInterceptor(endPoint))
                .addInterceptor(loggingInterceptor())
        return builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS).build()
    }

    /**
     * This function is used to create the header Interceptor instance
     *
     * @param endPoint EndPoint interface which holds the necessary data to do retrofit call
     * @return Interceptor instance
     */
    private fun getHeadersInterceptor(endPoint: EndPoint): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val request = originalRequest.newBuilder()
            request.header(
                Constant.HEADER_AUTHORIZATION,
                "${AppDataStorage.getAppPrefInstance()?.tokenType} ${AppDataStorage.getAppPrefInstance()?.accessToken ?: ""}",
            )
            for (key in endPoint.header?.keys!!) {
                request.header(key, endPoint.header!![key].toString())
            }
            request.method(originalRequest.method, originalRequest.body)
            chain.proceed(request.build())
        }
    }

    /**
     * This function is used to create the logger Interceptor instance
     * @return Interceptor instance
     */
    private fun loggingInterceptor(): Interceptor {
        val interceptor =
            HttpLoggingInterceptor { message: String? ->
                DebugPrint.d(TAG, message ?: "")
            }
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }
}
