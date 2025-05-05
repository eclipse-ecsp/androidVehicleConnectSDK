package org.eclipse.ecsp.notificationservice.endpoint

import org.eclipse.ecsp.environment.EnvironmentManager
import org.eclipse.ecsp.helper.Constant
import org.eclipse.ecsp.helper.Constant.ALERT_TYPE
import org.eclipse.ecsp.helper.Constant.CONTACT_ID
import org.eclipse.ecsp.helper.Constant.DEVICE_ID
import org.eclipse.ecsp.helper.Constant.LOCALE
import org.eclipse.ecsp.helper.Constant.USER_ID
import org.eclipse.ecsp.helper.Constant.VEHICLE_ID
import org.eclipse.ecsp.network.EndPoint
import org.eclipse.ecsp.network.RequestMethod

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

    /**
     * Sealed class object represent as NotificationAlert API
     */
    data object NotificationAlert : NotificationEndPoint(NOTIFICATION_ALERT)

    /**
     * Sealed class object represent as NotificationConfig API
     */
    data object NotificationConfig : NotificationEndPoint(NOTIFICATION_CONFIG)

    /**
     * Setting the Base url from environment details based on the selected sealed class object
     */
    override var baseUrl: String? =
        when (this.name) {
            NOTIFICATION_ALERT,
            NOTIFICATION_CONFIG,
            -> EnvironmentManager.environment()?.baseUrl.toString()

            else -> ""
        }

    /**
     * Setting the path based on the selected sealed class object
     */
    override var path: String? =
        when (this.name) {
            NOTIFICATION_CONFIG -> {
                "/v1/users/${USER_ID}/vehicles/${VEHICLE_ID}/contacts/${CONTACT_ID}/notifications/config"
            }

            NOTIFICATION_ALERT -> "/v3/devices/${DEVICE_ID}/alerts/${ALERT_TYPE}"
            else -> ""
        }

    /**
     * Setting the method based on the selected sealed class object
     */
    override var method: RequestMethod? =
        when (this.name) {
            NOTIFICATION_CONFIG -> RequestMethod.Patch
            else -> RequestMethod.Get
        }

    /**
     * Setting the headers based on the selected sealed class object
     */
    override var header: HashMap<String, String>? =
        when (this.name) {
            NOTIFICATION_ALERT, NOTIFICATION_CONFIG -> {
                HashMap<String, String>().apply {
                    put(Constant.HEADER_ACCEPT, Constant.HEADER_APPLICATION_JSON)
                    put(Constant.HEADER_ACCEPT_LANGUAGE, LOCALE)
                }
            }

            else -> HashMap()
        }

    /**
     * Setting the body
     */
    override var body: Any? = Any()
}
