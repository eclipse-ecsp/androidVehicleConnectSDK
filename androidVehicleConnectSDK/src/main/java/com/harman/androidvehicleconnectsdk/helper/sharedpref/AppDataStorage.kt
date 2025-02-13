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
import com.harman.androidvehicleconnectsdk.network.debugprint.DebugPrint
import kotlin.concurrent.Volatile

/**
 * AppDataStorage class is a singleton class to hold the instance of AppPreference class instance
 * Contains only Static functions
 *
 */
class AppDataStorage {
    companion object {
        private val TAG = AppDataStorage::class.java.name

        @Volatile
        private var instance: AppDataStorage? = null

        @Volatile
        private var appPreference: AppPreference? = null

        /**
         * This static function is to initiate the instance of AppPreference class
         * and initiate the instance of AppDataStorage class object
         *
         * @param context Application context which is required to initiate other class instance
         */
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                appPreference = AppPreference(context)
                instance ?: AppDataStorage().also { instance = it }
                DebugPrint.d(TAG, "App storage initialized")
            }

        /**
         * This function serve the instance of AppPreference class instance
         * which is created by getInstance(context: Context) function
         *
         * @return AppPreference class instance
         */
        fun getAppPrefInstance(): AppPreference? {
            return appPreference
        }

    }
}