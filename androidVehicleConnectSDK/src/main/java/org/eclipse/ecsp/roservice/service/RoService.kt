package org.eclipse.ecsp.roservice.service

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
import org.eclipse.ecsp.helper.Constant
import org.eclipse.ecsp.helper.Constant.RO_REGEX
import org.eclipse.ecsp.helper.getAlphaNumericId
import org.eclipse.ecsp.helper.response.CustomMessage
import org.eclipse.ecsp.roservice.endpoint.RoEndPoint
import org.eclipse.ecsp.roservice.model.RemoteOperationState
import org.eclipse.ecsp.roservice.model.RoEventHistoryResponse
import org.eclipse.ecsp.roservice.model.RoRequestData
import org.eclipse.ecsp.roservice.model.RoStatusResponse
import org.eclipse.ecsp.roservice.repository.RoRepositoryInterface
import javax.inject.Inject

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
        val INSTANCE: RoService by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { RoService() }
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
     * @return [CustomMessage] of [RoStatusResponse]
     */
    override suspend fun updateROStateRequest(
        userId: String,
        vehicleId: String,
        percent: Int?,
        duration: Int?,
        remoteOperationState: RemoteOperationState,
    ): CustomMessage<RoStatusResponse> {
        val endPoint = RoEndPoint.UpdateRemoteOperation
        val tempHeader =
            endPoint.header?.apply {
                put(Constant.REQUEST_ID, getAlphaNumericId(RO_REGEX))
                put(Constant.SESSION_ID, System.currentTimeMillis().toString())
            }
        val customEndPoint =
            CustomEndPoint(
                endPoint.baseUrl ?: "",
                "${
                    (
                        endPoint.path?.replace(Constant.USER_ID, userId)
                            ?.replace(Constant.VEHICLE_ID, vehicleId)
                    )
                }${remoteOperationState.roEndPoint}",
                endPoint.method,
                tempHeader,
                RoRequestData(remoteOperationState.state!!, percent, duration),
            )
        return roRepositoryInterface.updateROStateRequest(customEndPoint)
    }

    /**
     * This function is to get the RO history of the provided vehicle
     *
     * @param userId this is user id of user logged in
     * @param vehicleId vehicle id which the RO state to change
     * @return [CustomMessage] with [List] of [RoEventHistoryResponse]
     */
    override suspend fun getRemoteOperationHistory(
        userId: String,
        vehicleId: String,
    ): CustomMessage<List<RoEventHistoryResponse>> {
        val endPoint = RoEndPoint.RemoteOperationHistory
        val tempHeader =
            endPoint.header?.apply {
                put(Constant.REQUEST_ID, getAlphaNumericId(RO_REGEX))
                put(Constant.SESSION_ID, System.currentTimeMillis().toString())
            }
        val customEndPoint =
            CustomEndPoint(
                endPoint.baseUrl ?: "",
                "${
                    (
                        endPoint.path?.replace(Constant.USER_ID, userId)
                            ?.replace(Constant.VEHICLE_ID, vehicleId)
                    )
                }${HISTORY_PATH}",
                endPoint.method,
                tempHeader,
                endPoint.body,
            )
        return roRepositoryInterface.getRemoteOperationHistory(customEndPoint)
    }

    /**
     * This function is to check the RO state
     *
     * @param userId this is user id of user logged in
     * @param vehicleId vehicle id which the RO state to change
     * @param roRequestId this is Ro request id gets after the updating of state
     * @return [CustomMessage] of [RoEventHistoryResponse]
     */
    override suspend fun checkRemoteOperationRequestStatus(
        userId: String,
        vehicleId: String,
        roRequestId: String,
    ): CustomMessage<RoEventHistoryResponse> {
        val endPoint = RoEndPoint.RemoteOperationRequestStatus
        val tempHeader =
            endPoint.header?.apply {
                put(Constant.REQUEST_ID, getAlphaNumericId(RO_REGEX))
                put(Constant.SESSION_ID, System.currentTimeMillis().toString())
            }
        val customEndPoint =
            CustomEndPoint(
                endPoint.baseUrl ?: "",
                "${
                    (
                        endPoint.path?.replace(Constant.USER_ID, userId)
                            ?.replace(Constant.VEHICLE_ID, vehicleId)
                    )
                }${REQUEST_PATH}/$roRequestId",
                endPoint.method,
                tempHeader,
                endPoint.body,
            )
        return roRepositoryInterface.checkRemoteOperationRequestStatus(customEndPoint)
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
     * @return [CustomMessage] of [RoStatusResponse] value as response
     */
    suspend fun updateROStateRequest(
        userId: String,
        vehicleId: String,
        percent: Int? = null,
        duration: Int? = null,
        remoteOperationState: RemoteOperationState,
    ): CustomMessage<RoStatusResponse>

    /**
     * represents to get the RO history request
     *
     * @param userId holds the user id value
     * @param vehicleId holds the vehicleId value
     * @return [CustomMessage] of [RoEventHistoryResponse] value as response
     */
    suspend fun getRemoteOperationHistory(
        userId: String,
        vehicleId: String,
    ): CustomMessage<List<RoEventHistoryResponse>>

    /**
     * represents to check the RO request status
     *
     * @param userId holds the user id value
     * @param vehicleId holds the vehicleId value
     * @param roRequestId holds the ro request id
     * @return [CustomMessage] of [RoEventHistoryResponse] value as response
     */
    suspend fun checkRemoteOperationRequestStatus(
        userId: String,
        vehicleId: String,
        roRequestId: String,
    ): CustomMessage<RoEventHistoryResponse>

    companion object {
        /**
         * This function is used by client application to get the instance of RoService class
         *
         * @return RoService object
         */
        @JvmStatic
        fun roServiceInterface(): RoServiceInterface = RoService.INSTANCE
    }
}
