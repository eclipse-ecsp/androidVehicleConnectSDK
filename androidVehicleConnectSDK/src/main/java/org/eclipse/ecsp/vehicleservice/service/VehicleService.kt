package org.eclipse.ecsp.vehicleservice.service
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
import org.eclipse.ecsp.CustomEndPoint
import org.eclipse.ecsp.helper.AppManager
import org.eclipse.ecsp.helper.response.CustomMessage
import org.eclipse.ecsp.vehicleservice.endpoint.VehicleEndPoint
import org.eclipse.ecsp.vehicleservice.model.DeviceAssociationListData
import org.eclipse.ecsp.vehicleservice.model.DeviceVerificationData
import org.eclipse.ecsp.vehicleservice.model.TerminateDeviceData
import org.eclipse.ecsp.vehicleservice.model.deviceassociation.AssociatedDeviceInfo
import org.eclipse.ecsp.vehicleservice.model.deviceassociation.AssociationData
import org.eclipse.ecsp.vehicleservice.model.vehicleprofile.PostVehicleAttributeData
import org.eclipse.ecsp.vehicleservice.model.vehicleprofile.VehicleProfileData
import org.eclipse.ecsp.vehicleservice.repository.VehicleRepoInterface
import javax.inject.Inject

/**
 * VehicleService class is used by client application to call the Vehicle related functions
 */
class VehicleService : VehicleServiceInterface {
    @Inject
    lateinit var iVehicleRepository: VehicleRepoInterface

    init {
        AppManager.getAppComponent().inject(this)
    }

    companion object {
        val INSTANCE: VehicleService by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { VehicleService() }
    }

    /**
     * This function is used to get associated device list,
     * This function invokes the vehicle repository functions to get the result
     *
     * @return [CustomMessage] of [DeviceAssociationListData] as response value
     */
    override suspend fun associatedDeviceList(): CustomMessage<DeviceAssociationListData> {
        return iVehicleRepository.associatedDeviceList()
    }

    /**
     * This function is used to validate the device imei using API service,
     * This function invokes the vehicle repository functions to get the result
     *
     * @param imeiNumber device IMEI number
     * @return [CustomMessage] of [DeviceVerificationData] as response value
     */
    override suspend fun verifyDeviceImei(imeiNumber: String): CustomMessage<DeviceVerificationData> {
        val endPoint = VehicleEndPoint.VerifyDevice
        val customEndPoint =
            CustomEndPoint(
                endPoint.baseUrl ?: "",
                endPoint.path + imeiNumber,
                endPoint.method,
                endPoint.header,
                endPoint.body,
            )
        return iVehicleRepository.verifyDeviceImei(customEndPoint)
    }

    /**
     * This function is used to associate new device using API call via retrofit service by invoking the vehicle repository functions
     *
     * @param serialNumber vehicle serial number
     * @return [CustomMessage] of [AssociatedDeviceInfo] as response value
     */
    override suspend fun associateDevice(serialNumber: String): CustomMessage<AssociatedDeviceInfo> {
        val endPoint = VehicleEndPoint.DeviceAssociation
        val customEndPoint =
            CustomEndPoint(
                endPoint.baseUrl ?: "",
                endPoint.path,
                endPoint.method,
                endPoint.header,
                AssociationData(mSerialNumber = serialNumber),
            )
        return iVehicleRepository.associateDevice(customEndPoint)
    }

    /**
     * This function is used to get the vehicle profile data of a device using API call via retrofit service by invoking the vehicle repository function
     *
     * @param deviceId device ID
     * @return [CustomMessage] of [VehicleProfileData] as response value
     */
    override suspend fun getVehicleProfile(deviceId: String): CustomMessage<VehicleProfileData> {
        val endPoint = VehicleEndPoint.VehicleProfile
        val customEndPoint =
            CustomEndPoint(
                endPoint.baseUrl ?: "",
                endPoint.path + deviceId,
                endPoint.method,
                endPoint.header,
                endPoint.body,
            )
        return iVehicleRepository.getVehicleProfile(customEndPoint)
    }

    /**
     * This function is used to update the vehicle profile data of a device using API call via retrofit service by invoking the vehicle repository function
     *
     * @param deviceId unique id of device
     * @param postVehicleAttributeData vehicle profile data need to pass with updated value
     * @return [CustomMessage] of [String] as response value
     */
    override suspend fun updateVehicleProfile(
        deviceId: String,
        postVehicleAttributeData: PostVehicleAttributeData,
    ): CustomMessage<String> {
        val endPoint = VehicleEndPoint.UpdateVehicleProfile
        val customEndPoint =
            CustomEndPoint(
                endPoint.baseUrl ?: "",
                endPoint.path + deviceId,
                endPoint.method,
                endPoint.header,
                postVehicleAttributeData,
            )
        return iVehicleRepository.updateVehicleProfile(customEndPoint)
    }

    /**
     * This function is to terminate the device associated with user using API call via retrofit service by invoking the vehicle repository function
     *
     * @param terminateDeviceData holds the device related data to do termination
     * @return [CustomMessage] of [String] as response value
     */
    override suspend fun terminateVehicle(terminateDeviceData: TerminateDeviceData): CustomMessage<String> {
        val endPoint = VehicleEndPoint.TerminateDevice
        val customEndPoint =
            CustomEndPoint(
                endPoint.baseUrl ?: "",
                endPoint.path,
                endPoint.method,
                endPoint.header,
                terminateDeviceData,
            )
        return iVehicleRepository.terminateDevice(customEndPoint)
    }
}

/**
 * VehicleServiceInterface is implemented by VehicleService to override the functions available for vehicle related service
 *
 */
interface VehicleServiceInterface {
    /**
     * Represents to get the associated device list
     *
     * @return [CustomMessage] of [DeviceAssociationListData] value as response
     */
    suspend fun associatedDeviceList(): CustomMessage<DeviceAssociationListData>

    /**
     * Represents to verify the IMEI of device
     *
     * @param imeiNumber holds device IMEI number
     * @return [CustomMessage] of [DeviceVerificationData] value as response
     */
    suspend fun verifyDeviceImei(imeiNumber: String): CustomMessage<DeviceVerificationData>

    /**
     * Represents to do device association
     *
     * @param serialNumber holds vehicle serial number
     * @return [CustomMessage] of [AssociatedDeviceInfo] value as response
     */
    suspend fun associateDevice(serialNumber: String): CustomMessage<AssociatedDeviceInfo>

    /**
     * Represents to get the vehicle profile details
     *
     * @param deviceId holds device id
     * @return [CustomMessage] of [VehicleProfileData] value as response
     */
    suspend fun getVehicleProfile(deviceId: String): CustomMessage<VehicleProfileData>

    /**
     * Represents to update the vehicle profile details
     *
     * @param deviceId holds device id
     * @param postVehicleAttributeData holds device [PostVehicleAttributeData]
     * @return [CustomMessage] of [String] value as response
     */
    suspend fun updateVehicleProfile(
        deviceId: String,
        postVehicleAttributeData: PostVehicleAttributeData,
    ): CustomMessage<String>

    /**
     * Represents to do termination of a vehicle
     *
     * @param terminateDeviceData holds the device data of terminating device
     * @return [CustomMessage] of [String] value as response
     */
    suspend fun terminateVehicle(terminateDeviceData: TerminateDeviceData): CustomMessage<String>

    companion object {
        /**
         * This function is used to get the instance of VehicleService class
         *
         * @return VehicleService instance
         */
        @JvmStatic
        fun vehicleServiceInterface(): VehicleServiceInterface = VehicleService.INSTANCE
    }
}
