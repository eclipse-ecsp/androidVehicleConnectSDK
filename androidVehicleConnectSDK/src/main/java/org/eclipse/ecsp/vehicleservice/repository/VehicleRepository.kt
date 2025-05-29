package org.eclipse.ecsp.vehicleservice.repository
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
import org.eclipse.ecsp.vehicleservice.endpoint.VehicleEndPoint
import org.eclipse.ecsp.vehicleservice.model.DeviceAssociationListData
import org.eclipse.ecsp.vehicleservice.model.DeviceVerificationData
import org.eclipse.ecsp.vehicleservice.model.deviceassociation.AssociatedDeviceInfo
import org.eclipse.ecsp.vehicleservice.model.vehicleprofile.VehicleProfileData
import javax.inject.Inject
import javax.inject.Singleton

/**
 * VehicleRepository class has the functions related to Vehicle API
 * Using retrofit service API calls are done here.
 */
@Singleton
class VehicleRepository
    @Inject
    constructor() : VehicleRepoInterface {
        @Inject
        lateinit var retrofitManager: IRetrofitManager

        init {
            AppManager.getAppComponent().inject(this)
        }

        /**
         * This function is used to trigger the GET associated device list using retrofit service.
         *
         * @return [CustomMessage]  of [DeviceAssociationListData] as response value
         */
        override suspend fun associatedDeviceList(): CustomMessage<DeviceAssociationListData> {
            retrofitManager.sendRequest(VehicleEndPoint.DeviceAssociationList)
                .also {
                    return if (it != null && it.isSuccessful) {
                        val data = Gson().fromJson<DeviceAssociationListData>(it.body().toString())
                        val resp = CustomMessage<DeviceAssociationListData>(Status.Success)
                        resp.setResponseData(data)
                        resp
                    } else {
                        networkError(it?.errorBody(), it?.code())
                    }
                }
        }

        /**
         * This function is used to validate the device imei using API service
         *
         * @param customEndPoint this holds the customized endpoints of API Call
         * @return [CustomMessage] of [DeviceVerificationData] as response value
         */
        override suspend fun verifyDeviceImei(customEndPoint: CustomEndPoint): CustomMessage<DeviceVerificationData> {
            retrofitManager.sendRequest(customEndPoint)
                .also {
                    return if (it != null && it.isSuccessful) {
                        val data = Gson().fromJson<DeviceVerificationData>(it.body().toString())
                        val resp = CustomMessage<DeviceVerificationData>(Status.Success)
                        resp.setResponseData(data)
                        resp
                    } else {
                        networkError(it?.errorBody(), it?.code())
                    }
                }
        }

        /**
         * This function is used to associate new device using API call via retrofit service
         *
         * @param customEndPoint this holds the customized endpoints of API Call
         * @return [CustomMessage] of [AssociatedDeviceInfo] as response value
         */
        override suspend fun associateDevice(customEndPoint: CustomEndPoint): CustomMessage<AssociatedDeviceInfo> {
            retrofitManager.sendRequest(customEndPoint)
                .also {
                    return if (it != null && it.isSuccessful) {
                        val data = Gson().fromJson<AssociatedDeviceInfo>(it.body().toString())
                        val resp = CustomMessage<AssociatedDeviceInfo>(Status.Success)
                        resp.setResponseData(data)
                        resp
                    } else {
                        networkError(it?.errorBody(), it?.code())
                    }
                }
        }

        /**
         * This function is used to get the vehicle profile data using API call via retrofit service
         *
         * @param customEndPoint this holds the customized endpoints of API Call
         * @return [CustomMessage] of [VehicleProfileData] as response value
         */
        override suspend fun getVehicleProfile(customEndPoint: CustomEndPoint): CustomMessage<VehicleProfileData> {
            retrofitManager.sendRequest(customEndPoint)
                .also {
                    return if (it != null && it.isSuccessful) {
                        val data = Gson().fromJson<VehicleProfileData>(it.body().toString())
                        val resp = CustomMessage<VehicleProfileData>(Status.Success)
                        resp.setResponseData(data)
                        resp
                    } else {
                        networkError(it?.errorBody(), it?.code())
                    }
                }
        }

        /**
         * This function is used to update the vehicle profile data using API call via retrofit service
         *
         * @param customEndPoint this holds the customized endpoints of API Call
         * @return [CustomMessage] of [String] as response value
         */
        override suspend fun updateVehicleProfile(customEndPoint: CustomEndPoint): CustomMessage<String> {
            retrofitManager.sendRequest(customEndPoint)
                .also {
                    return if (it != null && it.isSuccessful) {
                        val resp = CustomMessage<String>(Status.Success)
                        resp.setResponseData(it.body().toString())
                        resp
                    } else {
                        networkError(it?.errorBody(), it?.code())
                    }
                }
        }

        /**
         * This function is used to terminate the vehicle associated with the user using API call via retrofit service
         *
         * @param customEndPoint this holds the customized endpoints of API Call
         * @return [CustomMessage] of [String] as response value
         */
        override suspend fun terminateDevice(customEndPoint: CustomEndPoint): CustomMessage<String> {
            retrofitManager.sendRequest(customEndPoint)
                .also {
                    return if (it != null && it.isSuccessful) {
                        val resp = CustomMessage<String>(Status.Success)
                        resp.setResponseData(it.body().toString())
                        resp
                    } else {
                        networkError(it?.errorBody(), it?.code())
                    }
                }
        }
    }

/**
 * VehicleRepoInterface is implemented by VehicleRepository to override the functions available for vehicle services
 */
interface VehicleRepoInterface {
    /**
     * Represent to call associated device list API
     *
     * @return [CustomMessage] of [DeviceAssociationListData] as response value
     */
    suspend fun associatedDeviceList(): CustomMessage<DeviceAssociationListData>

    /**
     * Represent to call device imei verification API
     *
     * @param customEndPoint holds the end point of API
     * @return [CustomMessage] of [DeviceVerificationData] as response value
     */
    suspend fun verifyDeviceImei(customEndPoint: CustomEndPoint): CustomMessage<DeviceVerificationData>

    /**
     * Represent to call vehicle profile GET API
     *
     * @param customEndPoint holds the end point of API
     * @return [CustomMessage] of [VehicleProfileData] as response value
     */
    suspend fun getVehicleProfile(customEndPoint: CustomEndPoint): CustomMessage<VehicleProfileData>

    /**
     * Represents to call device associate API
     *
     * @param customEndPoint holds the end point of API
     * @return [CustomMessage] of [AssociatedDeviceInfo] as response value
     */
    suspend fun associateDevice(customEndPoint: CustomEndPoint): CustomMessage<AssociatedDeviceInfo>

    /**
     * Represents to call vehicle profile data updating API
     *
     * @param customEndPoint holds the end point of API
     * @return [CustomMessage] of [String] as response value
     */
    suspend fun updateVehicleProfile(customEndPoint: CustomEndPoint): CustomMessage<String>

    /**
     * Represents to call device termination API
     *
     * @param customEndPoint holds the end point of API
     * @return [CustomMessage] of [String] as response value
     */
    suspend fun terminateDevice(customEndPoint: CustomEndPoint): CustomMessage<String>
}
