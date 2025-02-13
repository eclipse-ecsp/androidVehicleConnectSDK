package com.harman.androidvehicleconnectsdk.notificationservice.endpoint

import com.harman.androidvehicleconnectsdk.environment.EnvironmentManager
import com.harman.androidvehicleconnectsdk.helper.Constant
import com.harman.androidvehicleconnectsdk.helper.Constant.ALERT_TYPE
import com.harman.androidvehicleconnectsdk.helper.Constant.CONTACT_ID
import com.harman.androidvehicleconnectsdk.helper.Constant.DEVICE_ID
import com.harman.androidvehicleconnectsdk.helper.Constant.USER_ID
import com.harman.androidvehicleconnectsdk.helper.Constant.VEHICLE_ID
import com.harman.androidvehicleconnectsdk.helper.getLocale
import com.harman.androidvehicleconnectsdk.network.EndPoint
import com.harman.androidvehicleconnectsdk.network.RequestMethod

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
sealed class NotificationEndPoint(val name: String) : EndPoint {
    companion object {
        private const val NOTIFICATION_ALERT = "NotificationAlert"
        private const val NOTIFICATION_CONFIG = "NotificationConfig"
    }

    data object NotificationAlert : NotificationEndPoint(NOTIFICATION_ALERT)
    data object NotificationConfig : NotificationEndPoint(NOTIFICATION_CONFIG)

    override var baseUrl: String? = when (this.name) {
        NOTIFICATION_ALERT,
        NOTIFICATION_CONFIG -> EnvironmentManager.environment()?.baseUrl.toString()

        else -> ""
    }
    override var path: String? = when (this.name) {
        NOTIFICATION_CONFIG -> {
            "/v1/users/${USER_ID}/vehicles/${VEHICLE_ID}/contacts/${CONTACT_ID}/notifications/config"
        }

        NOTIFICATION_ALERT -> "/v3/devices/${DEVICE_ID}/alerts/${ALERT_TYPE}"
        else -> ""
    }
    override var method: RequestMethod? = when (this.name) {
        NOTIFICATION_CONFIG -> RequestMethod.Patch
        else -> RequestMethod.Get
    }

    override var header: HashMap<String, String>? = when (this.name) {
        NOTIFICATION_ALERT, NOTIFICATION_CONFIG -> {
            HashMap<String, String>().apply {
                put(Constant.HEADER_ACCEPT, Constant.HEADER_APPLICATION_JSON)
                put(Constant.HEADER_ACCEPT_LANGUAGE, getLocale())
            }
        }

        else -> HashMap()
    }

    override var body: Any?= Any()
}
