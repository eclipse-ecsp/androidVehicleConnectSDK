package org.eclipse.ecsp.notificationservice.repository

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
import org.eclipse.ecsp.CustomEndPoint
import org.eclipse.ecsp.helper.AppManager
import org.eclipse.ecsp.helper.fromJson
import org.eclipse.ecsp.helper.networkError
import org.eclipse.ecsp.helper.response.CustomMessage
import org.eclipse.ecsp.helper.response.error.Status
import org.eclipse.ecsp.network.networkmanager.IRetrofitManager
import org.eclipse.ecsp.notificationservice.model.AlertAnalysisData
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
                    val resp = CustomMessage<String>(Status.Success)
                    resp.setResponseData("Successfully updated the notification configuration data")
                    customMessage(resp)
                } else {
                    customMessage(networkError(it?.errorBody(), it?.code()))
                }
            }
        }

        /**
         * This function is to call the API for getting the Alert Notification history using retrofit service
         * @param customEndPoint this holds the customized endpoints of API Call
         * @return [CustomMessage] contain the success or failure details
         */
        override suspend fun notificationAlertHistory(customEndPoint: CustomEndPoint): CustomMessage<AlertAnalysisData> {
            retrofitManager.sendRequest(customEndPoint).also {
                return if (it != null && it.isSuccessful) {
                    val data = Gson().fromJson<AlertAnalysisData>(it.body().toString())
                    val resp = CustomMessage<AlertAnalysisData>(Status.Success)
                    resp.setResponseData(data)
                    resp
                } else {
                    networkError(it?.errorBody(), it?.code())
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
     * @return [CustomMessage] contain the success or failure details
     */
    suspend fun notificationAlertHistory(customEndPoint: CustomEndPoint): CustomMessage<AlertAnalysisData>
}
