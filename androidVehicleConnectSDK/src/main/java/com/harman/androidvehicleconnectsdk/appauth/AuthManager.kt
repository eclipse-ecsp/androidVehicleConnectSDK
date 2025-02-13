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
import com.harman.androidvehicleconnectsdk.helper.AppManager
import com.harman.androidvehicleconnectsdk.helper.sharedpref.AppDataStorage

/**
 * AuthManager class which has function to save the token to shared preference memory
 *
 */
class AuthManager {
    companion object {
        var authInterface: AuthInterface? = null

        /**
         * Set the instance of AuthInterface, use through out the app lifecycle
         * @param authInterface represents the instance of the AuthInterface.
         */
        fun sharedInterface(authInterface: AuthInterface) {
            this.authInterface = authInterface
            setAuthStateValue()
        }

        /*
        * Save the values of token related details to Shared preference memory
        * */
        private fun setAuthStateValue() {
            if (authInterface?.accessToken != null && authInterface?.accessToken!!.isNotEmpty()) {
                AppDataStorage.getAppPrefInstance()?.isLoggedIn = true
                AppDataStorage.getAppPrefInstance()?.accessToken =
                    authInterface?.accessToken.toString()
                AppDataStorage.getAppPrefInstance()?.refreshToken =
                    authInterface?.refreshToken.toString()
                AppDataStorage.getAppPrefInstance()?.tokenType =
                    authInterface?.tokenType.toString()
                AppDataStorage.getAppPrefInstance()?.authTokenExpireTime =
                    authInterface?.accessTokenExpirationDate ?: 0
                AppManager.isRefreshTokenFailed.postValue(false)
            }
        }
    }

    /*
    * Used by SDK to save the token related details
    * */
    fun saveTokenState() {
        setAuthStateValue()
    }
}