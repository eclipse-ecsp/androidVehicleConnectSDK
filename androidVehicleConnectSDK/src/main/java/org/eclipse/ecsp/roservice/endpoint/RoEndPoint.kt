package org.eclipse.ecsp.roservice.endpoint
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
import org.eclipse.ecsp.environment.EnvironmentManager
import org.eclipse.ecsp.helper.Constant
import org.eclipse.ecsp.helper.Constant.LOCALE
import org.eclipse.ecsp.helper.Constant.ORIGIN_KEY
import org.eclipse.ecsp.helper.Constant.ORIGIN_VALUE
import org.eclipse.ecsp.helper.Constant.USER_ID
import org.eclipse.ecsp.helper.Constant.VEHICLE_ID
import org.eclipse.ecsp.network.EndPoint
import org.eclipse.ecsp.network.RequestMethod

/**
 * RoEndPoint sealed class is used to configure the Remote operation API endpoints and other details
 * This sealed class is implemented EndPoint Interface
 *
 * @property name Is used to provide the name of object of sealed class
 */
sealed class RoEndPoint(val name: String) : EndPoint {
    companion object {
        private const val REMOTE_OPERATION_UPDATE = "UpdateRemoteOperation"
        private const val REMOTE_OPERATION_HISTORY = "RemoteOperationHistory"
        private const val REMOTE_OPERATION_REQUEST_STATUS = "RemoteOperationRequestStatus"
    }

    data object UpdateRemoteOperation : RoEndPoint(REMOTE_OPERATION_UPDATE)

    data object RemoteOperationHistory : RoEndPoint(REMOTE_OPERATION_HISTORY)

    data object RemoteOperationRequestStatus : RoEndPoint(REMOTE_OPERATION_REQUEST_STATUS)

    /**
     * Endpoint interface implementation method to set the base url for RO
     */
    override var baseUrl: String? =
        when (this.name) {
            REMOTE_OPERATION_UPDATE, REMOTE_OPERATION_HISTORY, REMOTE_OPERATION_REQUEST_STATUS -> {
                EnvironmentManager.environment()?.baseUrl.toString()
            }

            else -> ""
        }

    /**
     * Endpoint interface implementation method to set the path (end point of base url) for RO
     */
    override var path: String? =
        when (this.name) {
            REMOTE_OPERATION_UPDATE, REMOTE_OPERATION_HISTORY, REMOTE_OPERATION_REQUEST_STATUS -> {
                "v1.1/users/${USER_ID}/vehicles/${VEHICLE_ID}/ro/"
            }

            else -> ""
        }

    /**
     * Endpoint interface implementation method to set the Request method for RO
     */
    override var method: RequestMethod? =
        when (this.name) {
            REMOTE_OPERATION_UPDATE -> RequestMethod.Put
            else -> {
                RequestMethod.Get
            }
        }

    /**
     * Endpoint interface implementation method to set the headers for RO
     */
    override var header: HashMap<String, String>? =
        when (this.name) {
            REMOTE_OPERATION_UPDATE, REMOTE_OPERATION_HISTORY, REMOTE_OPERATION_REQUEST_STATUS -> {
                HashMap<String, String>().apply {
                    put(Constant.HEADER_ACCEPT, Constant.HEADER_APPLICATION_JSON)
                    put(Constant.HEADER_ACCEPT_LANGUAGE, LOCALE)
                    put(ORIGIN_KEY, ORIGIN_VALUE)
                }
            }

            else -> HashMap()
        }

    /**
     * Endpoint interface implementation method to set the body for RO
     */
    override var body: Any? = Any()
}
