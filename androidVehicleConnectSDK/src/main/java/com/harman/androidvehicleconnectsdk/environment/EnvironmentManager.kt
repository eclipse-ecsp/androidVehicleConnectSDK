package com.harman.androidvehicleconnectsdk.environment

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
import com.harman.androidvehicleconnectsdk.helper.dataToJson
import com.harman.androidvehicleconnectsdk.helper.fromJson
import com.harman.androidvehicleconnectsdk.helper.sharedpref.AppDataStorage
import com.harman.androidvehicleconnectsdk.network.debugprint.DebugPrint

/**
 * EnvironmentManager class which has static functions
 * Used to store and fetch the environment details
 *
 */
class EnvironmentManager {
    companion object {
        private val TAG = EnvironmentManager::class.java.name
        private var environmentInstance: Environment? = null

        /**
         * This function is to retrieve the environment details from Shared preference memory
         *
         * @return Environment , holds the environment data
         */
        fun environment(): Environment? {
            if (environmentInstance == null) {
                environmentInstance =
                    AppDataStorage.getAppPrefInstance()?.environment?.let {
                        Gson().fromJson<Environment>(
                            it,
                        )
                    }
            }
            return environmentInstance
        }

        /**
         * This function is to store the Environment data to Shared preference memory
         *
         * @param environment holds the environment data
         */
        fun configure(environment: Environment) {
            environmentInstance = environment
            AppDataStorage.getAppPrefInstance()?.environment = Gson().dataToJson(environment)
            DebugPrint.d(TAG, "Environment configured")
        }
    }
}
