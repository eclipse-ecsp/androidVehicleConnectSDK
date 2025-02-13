package com.harman.androidvehicleconnectsdk.notificationservice.service

import android.text.TextUtils
import com.harman.androidvehicleconnectsdk.CustomEndPoint
import com.harman.androidvehicleconnectsdk.helper.AppManager
import com.harman.androidvehicleconnectsdk.helper.Constant
import com.harman.androidvehicleconnectsdk.helper.response.CustomMessage
import com.harman.androidvehicleconnectsdk.notificationservice.endpoint.NotificationEndPoint
import com.harman.androidvehicleconnectsdk.notificationservice.model.AlertAnalysisData
import com.harman.androidvehicleconnectsdk.notificationservice.model.NotificationConfigData
import com.harman.androidvehicleconnectsdk.notificationservice.repository.NotificationRepoInterface
import javax.inject.Inject

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
class NotificationService : NotificationServiceInterface {

    @Inject
    lateinit var notificationRepoInterface: NotificationRepoInterface

    init {
        AppManager.getAppComponent().inject(this)
    }

    /**
     * This function is to update the notification config for push notification
     *
     * @param userId this is user id of user logged-in
     * @param vehicleId vehicle id which the RO state to change
     * @param contactId this is the unique contact id by default it is {self}
     * @param notificationConfigList configuration data list
     * @param customMessage this is the call back function to pass the response value
     */
    override suspend fun updateNotificationConfig(
        userId: String,
        vehicleId: String,
        contactId: String?,
        notificationConfigList: ArrayList<NotificationConfigData>,
        customMessage: (CustomMessage<String>) -> Unit
    ) {
        val endPoint = NotificationEndPoint.NotificationConfig
        val path = "${
            endPoint.path?.replace(Constant.USER_ID, userId)?.replace(Constant.VEHICLE_ID, vehicleId)?.replace(
                Constant.CONTACT_ID, contactId?: Constant.CONTACT_SELF
            )
        }"
        val customEndPoint = CustomEndPoint(
            endPoint.baseUrl,
            path,
            endPoint.method,
            endPoint.header,
            notificationConfigList
        )
        notificationRepoInterface.updateNotificationConfig(customEndPoint, customMessage)
    }

    /**
     * This is the function used to get the notification alert history data
     *
     * @param deviceId device id of selected vehicle
     * @param alertTypes alert type list is expecting, result of api will be based on the alert type shared
     * @param since since timestamp of device associated
     * @param till until timestamp -> which can be current datetime or any timestamp after association
     * @param size size of the record
     * @param page page number
     * @param readStatus by default it is {unread}
     * @param customMessage this is the call back function to pass the response value
     */
    override suspend fun notificationAlertHistory(
        deviceId: String,
        alertTypes: List<String>,
        since: Long,
        till: Long,
        size: Int?,
        page: Int?,
        readStatus: String?,
        customMessage: (CustomMessage<AlertAnalysisData>) -> Unit
    ) {
        val endPoint = NotificationEndPoint.NotificationAlert
        val path = "${
            (endPoint.path?.replace(Constant.DEVICE_ID, deviceId))?.replace(
                Constant.ALERT_TYPE,
                TextUtils.join(",", alertTypes)
            )
        }?since=$since&until=$till&size=${size ?: 1}&page=${page ?: 1}&readstatus=${readStatus ?: "all"}"
        val customEndPoint = CustomEndPoint(
            endPoint.baseUrl?:"",
            path, endPoint.method, endPoint.header, endPoint.body
        )
        notificationRepoInterface.notificationAlertHistory(customEndPoint, customMessage)
    }

}

interface NotificationServiceInterface {
    suspend fun updateNotificationConfig(
        userId: String,
        vehicleId: String,
        contactId: String?,
        notificationConfigList: ArrayList<NotificationConfigData>,
        customMessage: (CustomMessage<String>) -> Unit
    )

    suspend fun notificationAlertHistory(
        deviceId: String,
        alertTypes: List<String>,
        since: Long,
        till: Long,
        size: Int?,
        page: Int?,
        readStatus: String?,
        customMessage: (CustomMessage<AlertAnalysisData>) -> Unit
    )

    companion object {
        /**
         * This function is used by client application to get the instance of NotificationService class
         *
         * @return NotificationService object
         */
        @JvmStatic
        fun notificationServiceInterface(): NotificationServiceInterface = NotificationService()
    }
}