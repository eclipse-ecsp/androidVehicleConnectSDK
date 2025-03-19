package com.harman.androidvehicleconnectsdk.notificationservice.repository
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
import com.harman.androidvehicleconnectsdk.helper.AppManager
import com.harman.androidvehicleconnectsdk.helper.fromJson
import com.harman.androidvehicleconnectsdk.helper.networkError
import com.harman.androidvehicleconnectsdk.helper.networkResponse
import com.harman.androidvehicleconnectsdk.helper.response.CustomMessage
import com.harman.androidvehicleconnectsdk.network.networkmanager.IRetrofitManager
import com.harman.androidvehicleconnectsdk.notificationservice.model.AlertAnalysisData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository
    @Inject
    constructor() : NotificationRepoInterface {
        @Inject
        lateinit var retrofitManager: IRetrofitManager

        init {
            AppManager.getAppComponent().inject(this)
        }

        /**
         * This function is to call the API for updating the Notification config using retrofit service
         * @param customEndPoint this holds the customized endpoints of API Call
         * @param customMessage this is the call back function to pass the response value
         */
        override suspend fun updateNotificationConfig(
            customEndPoint: CustomEndPoint,
            customMessage: (CustomMessage<String>) -> Unit,
        ) {
            retrofitManager.sendRequest(customEndPoint).also {
                if (it != null && it.isSuccessful) {
                    customMessage(networkResponse("Successfully updated the notification configuration data"))
                } else {
                    customMessage(networkError(it?.errorBody(), it?.code()))
                }
            }
        }

        /**
         * This function is to call the API for getting the Alert Notification history using retrofit service
         * @param customEndPoint this holds the customized endpoints of API Call
         * @param customMessage this is the call back function to pass the response value
         */
        override suspend fun notificationAlertHistory(
            customEndPoint: CustomEndPoint,
            customMessage: (CustomMessage<AlertAnalysisData>) -> Unit,
        ) {
            retrofitManager.sendRequest(customEndPoint).also {
                if (it != null && it.isSuccessful) {
                    val data = Gson().fromJson<AlertAnalysisData>(it.body().toString())
                    customMessage(networkResponse(data))
                } else {
                    customMessage(networkError(it?.errorBody(), it?.code()))
                }
            }
        }
    }

interface NotificationRepoInterface {
    suspend fun updateNotificationConfig(
        customEndPoint: CustomEndPoint,
        customMessage: (CustomMessage<String>) -> Unit,
    )

    /**
     * Represents to call the notification alert history api
     *
     * @param customEndPoint data class of customEndPoint
     * @param customMessage higher order function to emit the CustomMessage<AlertAnalysisData> value
     */
    suspend fun notificationAlertHistory(
        customEndPoint: CustomEndPoint,
        customMessage: (CustomMessage<AlertAnalysisData>) -> Unit,
    )
}
