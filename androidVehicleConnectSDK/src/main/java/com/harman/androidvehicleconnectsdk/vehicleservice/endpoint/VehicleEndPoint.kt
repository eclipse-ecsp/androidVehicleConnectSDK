package com.harman.androidvehicleconnectsdk.vehicleservice.endpoint
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
import com.harman.androidvehicleconnectsdk.environment.EnvironmentManager
import com.harman.androidvehicleconnectsdk.helper.Constant.HEADER_ACCEPT
import com.harman.androidvehicleconnectsdk.helper.Constant.HEADER_ACCEPT_LANGUAGE
import com.harman.androidvehicleconnectsdk.helper.Constant.HEADER_APPLICATION_JSON
import com.harman.androidvehicleconnectsdk.helper.getLocale
import com.harman.androidvehicleconnectsdk.network.EndPoint
import com.harman.androidvehicleconnectsdk.network.RequestMethod
import com.harman.androidvehicleconnectsdk.vehicleservice.model.deviceassociation.AssociationData

/**
 * VehicleEndPoint sealed class is used to configure the Vehicle related API endpoints and other details
 * This sealed class is implemented EndPoint Interface
 *
 * @property name Is used to provide the object name of sealed class
 */
sealed class VehicleEndPoint(val name: String) : EndPoint {
    companion object {
        private const val DEVICE_ASSOCIATION_LIST = "DeviceAssociationList"
        private const val VERIFY_DEVICE = "VerifyDevice"
        private const val DEVICE_ASSOCIATION = "DeviceAssociation"
        private const val VEHICLE_PROFILE = "VehicleProfile"
        private const val UPDATE_VEHICLE_PROFILE = "UpdateVehicleProfile"
        private const val TERMINATE_DEVICE = "TerminateDevice"
    }

    data object DeviceAssociationList : VehicleEndPoint(DEVICE_ASSOCIATION_LIST)

    data object VerifyDevice : VehicleEndPoint(VERIFY_DEVICE)

    data object DeviceAssociation : VehicleEndPoint(DEVICE_ASSOCIATION)

    data object VehicleProfile : VehicleEndPoint(VEHICLE_PROFILE)

    data object UpdateVehicleProfile : VehicleEndPoint(UPDATE_VEHICLE_PROFILE)

    data object TerminateDevice : VehicleEndPoint(TERMINATE_DEVICE)

    /**
     * Endpoint interface implementation method to set the base url for Vehicle related API
     */
    override var baseUrl: String? =
        when (this.name) {
            DEVICE_ASSOCIATION_LIST, VERIFY_DEVICE, DEVICE_ASSOCIATION, VEHICLE_PROFILE,
            UPDATE_VEHICLE_PROFILE, TERMINATE_DEVICE,
            -> {
                EnvironmentManager.environment()?.baseUrl.toString()
            }

            else -> ""
        }

    /**
     * Endpoint interface implementation method to set the path (end point of base url) for Vehicle related API
     */
    override var path: String? =
        when (this.name) {
            DEVICE_ASSOCIATION_LIST -> "/v3/user/associations/"
            VERIFY_DEVICE -> "/v1/devices/details?imei="
            DEVICE_ASSOCIATION -> "/v3/user/devices/associate/"
            VEHICLE_PROFILE -> "/v1.0/vehicles?clientId="
            UPDATE_VEHICLE_PROFILE -> "/v1.0/vehicles/"
            TERMINATE_DEVICE -> "/v2/user/associations/terminate"
            else -> ""
        }

    /**
     * Endpoint interface implementation method to set the Request method for Vehicle related API
     */
    override var method: RequestMethod? =
        when (this.name) {
            DEVICE_ASSOCIATION, TERMINATE_DEVICE -> RequestMethod.Post
            UPDATE_VEHICLE_PROFILE -> RequestMethod.Patch
            else -> RequestMethod.Get
        }

    /**
     * Endpoint interface implementation method to set the headers for Vehicle related API
     */
    override var header: HashMap<String, String>? =
        when (this.name) {
            DEVICE_ASSOCIATION_LIST, VERIFY_DEVICE, DEVICE_ASSOCIATION,
            VEHICLE_PROFILE, UPDATE_VEHICLE_PROFILE, TERMINATE_DEVICE,
            -> {
                HashMap<String, String>().apply {
                    put(HEADER_ACCEPT, HEADER_APPLICATION_JSON)
                    put(HEADER_ACCEPT_LANGUAGE, getLocale())
                }
            }

            else -> HashMap()
        }

    /**
     * Endpoint interface implementation method to set the body for Vehicle related API
     */
    override var body: Any? =
        when (this.name) {
            DEVICE_ASSOCIATION -> AssociationData()
            UPDATE_VEHICLE_PROFILE, TERMINATE_DEVICE -> Any()
            else -> Any()
        }
}
