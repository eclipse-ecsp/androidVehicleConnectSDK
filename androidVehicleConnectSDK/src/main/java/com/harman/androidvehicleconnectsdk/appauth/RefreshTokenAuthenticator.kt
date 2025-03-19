package com.harman.androidvehicleconnectsdk.appauth

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
import com.harman.androidvehicleconnectsdk.CustomEndPoint
import com.harman.androidvehicleconnectsdk.environment.EnvironmentManager
import com.harman.androidvehicleconnectsdk.helper.AppManager
import com.harman.androidvehicleconnectsdk.helper.Constant.HEADER_AUTHORIZATION
import com.harman.androidvehicleconnectsdk.helper.fromJson
import com.harman.androidvehicleconnectsdk.helper.sharedpref.AppDataStorage
import com.harman.androidvehicleconnectsdk.network.debugprint.DebugPrint
import com.harman.androidvehicleconnectsdk.network.networkmanager.IRetrofitManager
import com.harman.androidvehicleconnectsdk.network.networkmanager.RetrofitProvider.retryCount
import com.harman.androidvehicleconnectsdk.userservice.endpoint.UserEndPoint
import com.harman.androidvehicleconnectsdk.userservice.model.TokenData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.lang.Exception

/**
 * RefreshTokenAuthenticator class which is used to trigger the refresh token API
 * Class is implementing an interface of okhttp3 lib
 * This class get invoked only if the any API fails with 401 error code
 * Instance of this class will be hold by Retrofit.
 *
 */
class RefreshTokenAuthenticator : Authenticator {
    companion object {
        private val TAG = RefreshTokenAuthenticator::class.java.name
    }

    /**
     * This function gets invoked only once any API call failed with error code 401 via retrofit,
     * This function is part of okhttp3 library service
     *
     * @param route route Address of okhttp3 service request made by application
     * @param response response body of okhttp3 service
     * @return Http request of okhttp3 service
     */
    override fun authenticate(
        route: Route?,
        response: Response,
    ): Request? {
        return if (retryCount <= 5) {
            runBlocking {
                getUpdatedToken()
            }
            response.request.newBuilder()
                .header(
                    HEADER_AUTHORIZATION,
                    "${AppDataStorage.getAppPrefInstance()?.tokenType} ${AppDataStorage.getAppPrefInstance()?.accessToken ?: ""}",
                )
                .build()
        } else {
            null
        }
    }

    /**
     * This suspend function triggers the refresh token API.
     * Response details are saved inside the AuthInterface
     *
     */
    private suspend fun getUpdatedToken() {
        val iRetrofitManager = IRetrofitManager.getRetrofitManager()
        val job =
            CoroutineScope(IO).async {
                val userEndPoint = UserEndPoint.RefreshToken
                val endPoint =
                    CustomEndPoint(
                        EnvironmentManager.environment()?.signinUrl.toString(),
                        userEndPoint.path + AppDataStorage.getAppPrefInstance()?.refreshToken,
                        userEndPoint.method,
                        userEndPoint.header,
                        userEndPoint.body,
                    )
                iRetrofitManager.sendRequest(endPoint)
            }
        try {
            val tokenData = Gson().fromJson<TokenData?>(job.await()?.body().toString())
            if (tokenData != null) {
                AuthManager.authInterface?.accessToken = tokenData.mAccessToken!!
                AuthManager.authInterface?.refreshToken = tokenData.mRefreshToken!!
                AuthManager.authInterface?.tokenType = tokenData.mTokenType!!
                AuthManager.authInterface?.accessTokenExpirationDate =
                    tokenData.mExpiresIn
                AppManager.isRefreshTokenFailed.postValue(false)
            } else {
                retryCount += 1
                DebugPrint.e("REFRESH_TOKEN", "Refresh token failed: ${retryCount + 1} times")
                if (retryCount > 5) {
                    AppManager.isRefreshTokenFailed.postValue(true)
                    AppDataStorage.getAppPrefInstance()?.removeAll()
                }
            }
        } catch (e: Exception) {
            DebugPrint.e("REFRESH_TOKEN", "Refresh token failed: ${retryCount + 1} times")
            retryCount += 1
            DebugPrint.e(TAG, e.printStackTrace())
        }
    }
}
