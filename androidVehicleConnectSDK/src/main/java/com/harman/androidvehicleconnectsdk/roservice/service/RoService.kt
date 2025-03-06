package com.harman.androidvehicleconnectsdk.roservice.service

import com.harman.androidvehicleconnectsdk.CustomEndPoint
import com.harman.androidvehicleconnectsdk.helper.AppManager
import com.harman.androidvehicleconnectsdk.helper.Constant
import com.harman.androidvehicleconnectsdk.helper.response.CustomMessage
import com.harman.androidvehicleconnectsdk.roservice.endpoint.RoEndPoint
import com.harman.androidvehicleconnectsdk.roservice.model.RemoteOperationState
import com.harman.androidvehicleconnectsdk.roservice.model.RoEventHistoryResponse
import com.harman.androidvehicleconnectsdk.roservice.model.RoRequestData
import com.harman.androidvehicleconnectsdk.roservice.model.RoStatusResponse
import com.harman.androidvehicleconnectsdk.roservice.repository.RoRepositoryInterface
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
 * RoService class is used by client application to call the RO suspend functions
 * This class have RO suspend functions
 *
 */
class RoService : RoServiceInterface {
    @Inject
    lateinit var roRepositoryInterface: RoRepositoryInterface

    companion object {
        private const val HISTORY_PATH = "history"
        private const val REQUEST_PATH = "requests"
    }

    init {
        AppManager.getAppComponent().inject(this)
    }
    /**
     * This function is to update the RO state
     *
     * @param userId this is user id of user logged in
     * @param vehicleId vehicle id which the RO state to change
     * @param remoteOperationState represents the RO state
     * @param percent RO state percent
     * @param duration RO state duration
     * @param customMessage this is the call back function to pass the response value
     */
    override suspend fun updateROStateRequest(
        userId: String,
        vehicleId: String,
        percent: Int?,
        duration: Int?,
        remoteOperationState: RemoteOperationState,
        customMessage: (CustomMessage<RoStatusResponse>) -> Unit
    ) {
        val endPoint = RoEndPoint.UpdateRemoteOperation
        val tempHeader = endPoint.header
        tempHeader?.put(Constant.REQUEST_ID, System.currentTimeMillis().toString())
        tempHeader?.put(Constant.SESSION_ID, System.currentTimeMillis().toString())
        val customEndPoint = CustomEndPoint(
            endPoint.baseUrl?:"",
            "${
                (endPoint.path?.replace(Constant.USER_ID, userId)
                    ?.replace(Constant.VEHICLE_ID, vehicleId))
            }${remoteOperationState.roEndPoint}",
            endPoint.method,
            tempHeader,
            RoRequestData(remoteOperationState.state!!, percent, duration)
        )
        roRepositoryInterface.updateROStateRequest(customEndPoint, customMessage)
    }
    /**
     * This function is to get the RO history of the provided vehicle
     *
     * @param userId this is user id of user logged in
     * @param vehicleId vehicle id which the RO state to change
     * @param customMessage this is the call back function to pass the response value
     */
    override suspend fun getRemoteOperationHistory(
        userId: String,
        vehicleId: String,
        customMessage: (CustomMessage<List<RoEventHistoryResponse>>) -> Unit
    ) {
        val endPoint  = RoEndPoint.RemoteOperationHistory
        val tempHeader = endPoint.header
        tempHeader?.put(Constant.REQUEST_ID, System.currentTimeMillis().toString())
        tempHeader?.put(Constant.SESSION_ID, System.currentTimeMillis().toString())
        val customEndPoint = CustomEndPoint(
            endPoint.baseUrl?:"",
            "${
                (endPoint.path?.replace(Constant.USER_ID, userId)
                    ?.replace(Constant.VEHICLE_ID, vehicleId))
            }${HISTORY_PATH}",
            endPoint.method,
            tempHeader,
            endPoint.body
        )
        roRepositoryInterface.getRemoteOperationHistory(customEndPoint, customMessage)
    }
    /**
     * This function is to check the RO state
     *
     * @param userId this is user id of user logged in
     * @param vehicleId vehicle id which the RO state to change
     * @param roRequestId this is Ro request id gets after the updating of state
     * @param customMessage this is the call back function to pass the response value
     */
    override suspend fun checkRemoteOperationRequestStatus(
        userId: String,
        vehicleId: String,
        roRequestId: String,
        customMessage: (CustomMessage<RoEventHistoryResponse>) -> Unit
    ) {
        val endPoint = RoEndPoint.RemoteOperationRequestStatus
        val tempHeader = endPoint.header
        tempHeader?.put(Constant.REQUEST_ID, System.currentTimeMillis().toString())
        tempHeader?.put(Constant.SESSION_ID, System.currentTimeMillis().toString())
        val customEndPoint = CustomEndPoint(
            endPoint.baseUrl?:"",
            "${
                (endPoint.path?.replace(Constant.USER_ID, userId)
                    ?.replace(Constant.VEHICLE_ID, vehicleId))
            }${REQUEST_PATH}/${roRequestId}",
            endPoint.method,
            tempHeader,
            endPoint.body
        )
        roRepositoryInterface.checkRemoteOperationRequestStatus(customEndPoint, customMessage)
    }
}

interface RoServiceInterface {
    /**
     * represents to api the update API for ro state
     *
     * @param userId holds the user id value
     * @param vehicleId holds the vehicleId value
     * @param percent holds the percent value
     * @param duration holds the duration value
     * @param remoteOperationState holds the remote Operation State value
     * @param customMessage higher order function to emit the [CustomMessage] value as response
     */
    suspend fun updateROStateRequest(userId: String, vehicleId: String, percent: Int?=null, duration: Int?=null,
        remoteOperationState: RemoteOperationState,
        customMessage: (CustomMessage<RoStatusResponse>) -> Unit
    )

    /**
     * represents to get the RO history request
     *
     * @param userId holds the user id value
     * @param vehicleId holds the vehicleId value
     * @param customMessage higher order function to emit the [CustomMessage] value as response
     */
    suspend fun getRemoteOperationHistory(
        userId: String,
        vehicleId: String,
        customMessage: (CustomMessage<List<RoEventHistoryResponse>>) -> Unit
    )

    /**
     * represents to check the RO request status
     *
     * @param userId holds the user id value
     * @param vehicleId holds the vehicleId value
     * @param roRequestId holds the ro request id
     * @param customMessage higher order function to emit the [CustomMessage] value as response
     */
    suspend fun checkRemoteOperationRequestStatus(
        userId: String,
        vehicleId: String,
        roRequestId: String,
        customMessage: (CustomMessage<RoEventHistoryResponse>) -> Unit
    )

    companion object {
        /**
         * This function is used by client application to get the instance of RoService class
         *
         * @return RoService object
         */
        @JvmStatic
        fun roServiceInterface(): RoServiceInterface = RoService()
    }
}