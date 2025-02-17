package com.harman.androidvehicleconnectsdk.vehicleservice.service

import com.harman.androidvehicleconnectsdk.CustomEndPoint
import com.harman.androidvehicleconnectsdk.helper.AppManager
import com.harman.androidvehicleconnectsdk.helper.response.CustomMessage
import com.harman.androidvehicleconnectsdk.vehicleservice.endpoint.VehicleEndPoint
import com.harman.androidvehicleconnectsdk.vehicleservice.model.DeviceAssociationListData
import com.harman.androidvehicleconnectsdk.vehicleservice.model.DeviceVerificationData
import com.harman.androidvehicleconnectsdk.vehicleservice.model.TerminateDeviceData
import com.harman.androidvehicleconnectsdk.vehicleservice.model.deviceassociation.AssociatedDeviceInfo
import com.harman.androidvehicleconnectsdk.vehicleservice.model.deviceassociation.AssociationData
import com.harman.androidvehicleconnectsdk.vehicleservice.model.vehicleprofile.PostVehicleAttributeData
import com.harman.androidvehicleconnectsdk.vehicleservice.model.vehicleprofile.VehicleProfileData
import com.harman.androidvehicleconnectsdk.vehicleservice.repository.VehicleRepoInterface
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
/**
 * VehicleService class is used by client application to call the Vehicle related functions
 */
class VehicleService : VehicleServiceInterface {
    @Inject
    lateinit var iVehicleRepository: VehicleRepoInterface

    init {
        AppManager.getAppComponent().inject(this)
    }

    /**
     * This function is used to get associated device list,
     * This function invokes the vehicle repository functions to get the result
     *
     * @param customMessage this is the call back function to pass the API response value
     */
    override suspend fun associatedDeviceList(customMessage: (CustomMessage<DeviceAssociationListData>) -> Unit) {
        iVehicleRepository.associatedDeviceList(customMessage)
    }

    /**
     * This function is used to validate the device imei using API service,
     * This function invokes the vehicle repository functions to get the result
     *
     * @param imeiNumber device IMEI number
     * @param customMessage this is the call back function to pass the API response value
     */
    override suspend fun verifyDeviceImei(
        imeiNumber: String,
        customMessage: (CustomMessage<DeviceVerificationData>) -> Unit
    ) {
        val endPoint = VehicleEndPoint.VerifyDevice
        val customEndPoint = CustomEndPoint(
            endPoint.baseUrl ?: "",
            endPoint.path + imeiNumber,
            endPoint.method,
            endPoint.header,
            endPoint.body
        )
        iVehicleRepository.verifyDeviceImei(customEndPoint, customMessage)
    }

    /**
     * This function is used to associate new device using API call via retrofit service by invoking the vehicle repository functions
     *
     * @param imeiNumber device IMEI number
     * @param customMessage this is the call back function to pass the API response value
     */
    override suspend fun associateDevice(
        imeiNumber: String,
        customMessage: (CustomMessage<AssociatedDeviceInfo>) -> Unit
    ) {
        val endPoint = VehicleEndPoint.DeviceAssociation
        val customEndPoint = CustomEndPoint(
            endPoint.baseUrl ?: "",
            endPoint.path,
            endPoint.method,
            endPoint.header,
            AssociationData(mImei = imeiNumber)
        )
        iVehicleRepository.associateDevice(customEndPoint,customMessage)
    }

    /**
     * This function is used to get the vehicle profile data of a device using API call via retrofit service by invoking the vehicle repository function
     *
     * @param deviceId device ID
     * @param customMessage this is the call back function to pass the API response value
     */
    override suspend fun getVehicleProfile(
        deviceId: String,
        customMessage: (CustomMessage<VehicleProfileData>) -> Unit
    ) {
        val endPoint = VehicleEndPoint.VehicleProfile
        val customEndPoint = CustomEndPoint(
            endPoint.baseUrl ?: "",
            endPoint.path + deviceId,
            endPoint.method,
            endPoint.header,
            endPoint.body
        )
        iVehicleRepository.getVehicleProfile(customEndPoint, customMessage)
    }

    /**
     * This function is used to update the vehicle profile data of a device using API call via retrofit service by invoking the vehicle repository function
     *
     * @param deviceId unique id of device
     * @param postVehicleAttributeData vehicle profile data need to pass with updated value
     * @param customMessage this is the call back function to pass the API response value
     */
    override suspend fun updateVehicleProfile(
        deviceId: String,
        postVehicleAttributeData: PostVehicleAttributeData,
        customMessage: (CustomMessage<String>) -> Unit
    ) {
        val endPoint = VehicleEndPoint.UpdateVehicleProfile
        val customEndPoint = CustomEndPoint(
            endPoint.baseUrl ?: "",
            endPoint.path + deviceId,
            endPoint.method,
            endPoint.header,
            postVehicleAttributeData
        )
        iVehicleRepository.updateVehicleProfile(customEndPoint, customMessage)
    }

    /**
     * This function is to terminate the device associated with user using API call via retrofit service by invoking the vehicle repository function
     *
     * @param terminateDeviceData holds the device related data to do termination
     * @param customMessage this is the call back function to pass the API response value
     */
    override suspend fun terminateVehicle(
        terminateDeviceData: TerminateDeviceData,
        customMessage: (CustomMessage<String>) -> Unit
    ) {
        val endPoint = VehicleEndPoint.TerminateDevice
        val customEndPoint = CustomEndPoint(
            endPoint.baseUrl ?: "",
            endPoint.path,
            endPoint.method,
            endPoint.header,
            terminateDeviceData
        )
        iVehicleRepository.terminateDevice(customEndPoint, customMessage)
    }
}

/**
 * VehicleServiceInterface is implemented by VehicleService to override the functions available for vehicle related service
 *
 */
interface VehicleServiceInterface {
    suspend fun associatedDeviceList(customMessage: (CustomMessage<DeviceAssociationListData>) -> Unit)
    suspend fun verifyDeviceImei(
        imeiNumber: String,
        customMessage: (CustomMessage<DeviceVerificationData>) -> Unit
    )

    suspend fun associateDevice(
        imeiNumber: String,
        customMessage: (CustomMessage<AssociatedDeviceInfo>) -> Unit
    )

    suspend fun getVehicleProfile(
        deviceId: String,
        customMessage: (CustomMessage<VehicleProfileData>) -> Unit
    )

    suspend fun updateVehicleProfile(
        deviceId: String,
        postVehicleAttributeData: PostVehicleAttributeData,
        customMessage: (CustomMessage<String>) -> Unit
    )

    suspend fun terminateVehicle(
        terminateDeviceData: TerminateDeviceData,
        customMessage: (CustomMessage<String>) -> Unit
    )

    companion object {
        /**
         * This function is used to get the instance of VehicleService class
         *
         * @return VehicleService instance
         */
        @JvmStatic
        fun vehicleServiceInterface(): VehicleServiceInterface = VehicleService()
    }
}