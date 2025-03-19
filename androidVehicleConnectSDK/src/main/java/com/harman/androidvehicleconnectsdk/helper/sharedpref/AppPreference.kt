package com.harman.androidvehicleconnectsdk.helper.sharedpref

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

/**
 * AppPreference class is implementing IAppPreference,
 * which has the functional variable used to save and access via shared preference
 *
 * @param context application context of the client application,
 * which is received via AppDataStorage class
 */
class AppPreference(context: Context) : IAppPreference {
    companion object {
        private const val VEHICLE_OEM_PREF = "vehicle_oem_pref"
        private const val ENVIRONMENT_DATA = "environment_data"
        private const val ACCESS_TOKEN = "access_token"
        private const val REFRESH_TOKEN = "refresh_token"
        private const val TOKEN_TYPE = "token_type"
        private const val AUTH_STATE = "auth_state"
        private const val AUTH_EXPIRE_TIME = "auth_expire_time"
        private const val FIRST_TIME = "first_time_login"
    }

    private var sharedPref = context.getSharedPreferences(VEHICLE_OEM_PREF, Context.MODE_PRIVATE)
    private var editor = sharedPref.edit()

    /**
     * This variable is capable of saving and getting the environment data from shared preference instance
     * @return environment data in string format
     */
    override var environment: String
        get() = getString(ENVIRONMENT_DATA)
        set(value) {
            saveString(ENVIRONMENT_DATA, value)
        }

    /**
     * This variable is capable of saving and getting the Token data from shared preference instance
     * @return accessToken data in string format
     */
    override var accessToken: String
        get() = getString(ACCESS_TOKEN)
        set(value) {
            saveString(ACCESS_TOKEN, value)
        }

    /**
     * This variable is capable of saving and getting the token Type data from shared preference instance
     * @return tokenType data in string format
     */
    override var tokenType: String
        get() = getString(TOKEN_TYPE)
        set(value) {
            saveString(TOKEN_TYPE, value)
        }

    /**
     * This variable is capable of saving and getting the refresh Token data from shared preference instance
     * @return refreshToken data in string format
     */
    override var refreshToken: String
        get() = getString(REFRESH_TOKEN)
        set(value) {
            saveString(REFRESH_TOKEN, value)
        }

    /**
     * This variable is capable of saving and getting the authState data from shared preference instance
     * @return authState data in string format
     */
    override var authState: String
        get() = getString(AUTH_STATE)
        set(value) {
            saveString(AUTH_STATE, value)
        }

    /**
     * This variable is capable of saving and getting the authTokenExpireTime data from shared preference instance
     * @return authTokenExpireTime data in string format
     */
    override var authTokenExpireTime: Long
        get() = getLong(AUTH_EXPIRE_TIME)
        set(value) {
            saveLong(AUTH_EXPIRE_TIME, value)
        }

    /**
     * This variable is capable of saving and getting the isLoggedIn boolean value from shared preference instance
     * @return isLoggedIn in boolean format
     */
    override var isLoggedIn: Boolean
        get() = getBoolean(FIRST_TIME)
        set(value) {
            saveBoolean(FIRST_TIME, value)
        }

    /**
     * This function is to remove the specific data which is saved under shared preference based on the key
     *
     * @param key used to remove the stored data belongs to this key which is saved under the shared preference instance
     */
    override fun remove(key: String) {
        editor.remove(key).apply()
    }

    /**
     * This function is to remove all data which is store under shared preference instance
     *
     */
    override fun removeAll() {
        editor.clear().apply()
    }

    /**
     * This function is to store the String value
     *
     * @param key String text used to store the value under shared preference instance
     * @param value Data which is need to store under shared preference instance
     */
    private fun saveString(
        key: String,
        value: String,
    ) {
        editor.putString(key, value).apply()
    }

    /**
     * This function is to retrieve the string data from shared preference instance using the key
     *
     * @param key String text used to get the value from shared preference instance
     * @param defaultValue Default value which will be passed if there is no data available
     * @return String value
     */
    private fun getString(
        key: String,
        defaultValue: String = "",
    ): String {
        return sharedPref.getString(key, defaultValue) ?: defaultValue
    }

    /**
     * This function is to store the Long value
     *
     * @param key String text used to store the value under shared preference instance
     * @param value Data which is need to store under shared preference instance
     */
    private fun saveLong(
        key: String,
        value: Long,
    ) {
        editor.putLong(key, value).apply()
    }

    /**
     * This function is to retrieve the string data from shared preference instance using the key
     *
     * @param key String text used to get the value from shared preference instance
     * @param defaultValue Default value which will be passed if there is no data available
     * @return Long value
     */
    private fun getLong(
        key: String,
        defaultValue: Long = 0,
    ): Long {
        return sharedPref.getLong(key, defaultValue)
    }

    /**
     * This function is to retrieve the Boolean data from shared preference instance using the key
     *
     * @param key String text used to get the value from shared preference instance
     * @param defaultValue Default value which will be passed if there is no data available
     * @return Boolean value
     */
    private fun getBoolean(
        key: String,
        defaultValue: Boolean = false,
    ): Boolean {
        return sharedPref.getBoolean(key, defaultValue)
    }

    /**
     * This function is to store the Boolean value
     *
     * @param key String text used to store the value under shared preference instance
     * @param value Data which is need to store under shared preference instance
     */
    private fun saveBoolean(
        key: String,
        value: Boolean,
    ) {
        editor.putBoolean(key, value)
    }
}
