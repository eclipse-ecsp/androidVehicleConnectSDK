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

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.harman.androidvehicleconnectsdk.helper.response.CustomMessage
import com.harman.androidvehicleconnectsdk.helper.sharedpref.AppDataStorage
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.TokenResponse

/**
 * This interface is used to hold token related data and have functions related to authentication
 *
 */
interface AuthInterface {
    /**
     * Variable used to hold access token data
     */
    var accessToken: String
        get() = AppDataStorage.getAppPrefInstance()?.accessToken ?: ""
        set(value) {AppDataStorage.getAppPrefInstance()?.accessToken = value}

    /**
     * Variable used to hold refresh token data
     */
    var refreshToken: String
        get() = AppDataStorage.getAppPrefInstance()?.refreshToken ?: ""
        set(value) {AppDataStorage.getAppPrefInstance()?.refreshToken = value}

    /**
     * Variable used to hold token type data
     */
    var tokenType: String
        get() = AppDataStorage.getAppPrefInstance()?.tokenType ?: ""
        set(value) {AppDataStorage.getAppPrefInstance()?.tokenType = value}

    /**
     * Variable used to hold scope data
     */
    var scope: String

    /**
     * Variable used to hold access token Expiration data
     */
    var accessTokenExpirationDate: Long
        get() = AppDataStorage.getAppPrefInstance()?.authTokenExpireTime ?: 0
        set(value) {AppDataStorage.getAppPrefInstance()?.authTokenExpireTime = value}

    var additionalParameters: HashMap<Any, Any>

    /**
     * This suspend function is used to implement the sign in process,
     * Function is a default implemented function
     *
     * @param result serve the customized message contains status,error and response
     */
    suspend fun signIn(result: (CustomMessage<Any>) -> Unit) { /* default implementation */ }

    /**
     * This suspend function is used to implement the sign Up process,
     * Function is a default implemented function
     *
     * @param result serve the customized message contains status,error and response
     */
    suspend fun signUp(result: (CustomMessage<Any>) -> Unit) { /* default implementation */ }

    /**
     * This suspend function is used to implement the sign out process,
     * Function is a default implemented function
     *
     * @param result serve the customized message contains status,error and response
     */
    suspend fun signOut(result: (CustomMessage<Any>) -> Unit) { /* default implementation */ }

    /**
     * This function is used to dispose the auth service,
     * Function is a default implemented function
     */
    fun disposeService() { /* default implementation */ }

    /**
     * This function is used to serve the token Response or auth error after parsing the OAuth service response
     *
     * @param response this is AuthorizationResponse coming from OAuth service
     * @param result parsed response is holds and serve
     */
    fun getAuthServices(
        response: AuthorizationResponse?,
        result: (
            tokenResponse: TokenResponse?,
            authorizationException: AuthorizationException?
        ) -> Unit
    ) {/* default implementation */ }

    companion object {
        /**
         * Reference to app Auth interface, user can directly call this function initialize the AuthInterface
         *
         * @param context represents the application context
         * @param launcher represents the ActivityResultLauncher of the application
         * @return AuthInterface object
         */
        @JvmStatic
        fun appAuthInterface(
            context: Context,
            launcher: ActivityResultLauncher<Intent>
        ): AuthInterface =
            AppAuthProvider(context, launcher)
    }
}