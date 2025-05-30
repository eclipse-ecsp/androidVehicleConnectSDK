package org.eclipse.ecsp.notificationservice.service

import android.text.TextUtils
import org.eclipse.ecsp.CustomEndPoint
import org.eclipse.ecsp.helper.AppManager
import org.eclipse.ecsp.helper.Constant
import org.eclipse.ecsp.helper.Constant.NOTIFICATION_REGEX
import org.eclipse.ecsp.helper.Constant.REQUEST_ID
import org.eclipse.ecsp.helper.getAlphaNumericId
import org.eclipse.ecsp.helper.response.CustomMessage
import org.eclipse.ecsp.notificationservice.endpoint.NotificationEndPoint
import org.eclipse.ecsp.notificationservice.model.AlertAnalysisData
import org.eclipse.ecsp.notificationservice.model.NotificationConfigData
import org.eclipse.ecsp.notificationservice.repository.NotificationRepoInterface
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

    companion object {
        val INSTANCE: NotificationService by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { NotificationService() }
    }

    /**
     * This function is to update the notification config for push notification
     *
     * @param userId this is user id of user logged-in
     * @param vehicleId vehicle id which the RO state to change
     * @param contactId this is the unique contact id by default it is {self}
     * @param notificationConfigList configuration data list
     * @return [String] value of [CustomMessage]
     */
    override suspend fun updateNotificationConfig(
        userId: String,
        vehicleId: String,
        contactId: String?,
        notificationConfigList: ArrayList<NotificationConfigData>,
    ): CustomMessage<String> {
        val endPoint = NotificationEndPoint.NotificationConfig
        val path = "${
            endPoint.path?.replace(Constant.USER_ID, userId)
                ?.replace(Constant.VEHICLE_ID, vehicleId)?.replace(
                    Constant.CONTACT_ID,
                    contactId ?: Constant.CONTACT_SELF,
                )
        }"
        val header =
            endPoint.header?.apply {
                put(REQUEST_ID, getAlphaNumericId(NOTIFICATION_REGEX))
            }
        val customEndPoint =
            CustomEndPoint(
                endPoint.baseUrl,
                path,
                endPoint.method,
                header,
                notificationConfigList,
            )
        return notificationRepoInterface.updateNotificationConfig(customEndPoint)
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
     * @return [CustomMessage] contain the success or failure details
     */
    override suspend fun notificationAlertHistory(
        deviceId: String,
        alertTypes: List<String>,
        since: Long,
        till: Long,
        size: Int?,
        page: Int?,
        readStatus: String?,
    ): CustomMessage<AlertAnalysisData> {
        val endPoint = NotificationEndPoint.NotificationAlert
        val path = "${
            (endPoint.path?.replace(Constant.DEVICE_ID, deviceId))?.replace(
                Constant.ALERT_TYPE,
                TextUtils.join(",", alertTypes),
            )
        }?since=$since&until=$till&size=${size ?: 1}&page=${page ?: 1}&readstatus=${readStatus ?: "all"}"
        val customEndPoint =
            CustomEndPoint(
                endPoint.baseUrl ?: "",
                path,
                endPoint.method,
                endPoint.header,
                endPoint.body,
            )
        return notificationRepoInterface.notificationAlertHistory(customEndPoint)
    }
}

interface NotificationServiceInterface {
    /**
     * Interface to update the notification configuration
     *
     * @param userId holds user id
     * @param vehicleId holds vehicle Id
     * @param contactId holds contact Id
     * @param notificationConfigList holds notification Config List
     * @return [CustomMessage] contain the success or failure details
     */
    suspend fun updateNotificationConfig(
        userId: String,
        vehicleId: String,
        contactId: String?,
        notificationConfigList: ArrayList<NotificationConfigData>,
    ): CustomMessage<String>

    /**
     * function is to get the notification alert history API
     *
     * @param deviceId holds device id
     * @param alertTypes holds alert types
     * @param since holds since date value
     * @param till holds till date value
     * @param size holds size value
     * @param page holds page value
     * @param readStatus holds read status of the alert
     * @return [CustomMessage] contain the success or failure details
     */
    suspend fun notificationAlertHistory(
        deviceId: String,
        alertTypes: List<String>,
        since: Long,
        till: Long,
        size: Int?,
        page: Int?,
        readStatus: String?,
    ): CustomMessage<AlertAnalysisData>

    companion object {
        /**
         * This function is used by client application to get the instance of NotificationService class
         *
         * @return NotificationService object
         */
        @JvmStatic
        fun notificationServiceInterface(): NotificationServiceInterface = NotificationService.INSTANCE
    }
}
