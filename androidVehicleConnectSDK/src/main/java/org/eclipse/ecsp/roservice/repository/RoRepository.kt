package org.eclipse.ecsp.roservice.repository

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
import org.eclipse.ecsp.roservice.model.RoEventHistoryResponse
import org.eclipse.ecsp.roservice.model.RoStatusResponse
import javax.inject.Inject
import javax.inject.Singleton

/**
 * RoRepository is used to call the API for remote operation feature
 * This have the functions to update RO state, get history of RO state and check status of RO state
 *
 */
@Singleton
class RoRepository
    @Inject
    constructor() : RoRepositoryInterface {
        @Inject
        lateinit var retrofitManager: IRetrofitManager

        init {
            AppManager.getAppComponent().inject(this)
        }

        /**
         * This function is to call the API for update the RO state using retrofit service
         * @param customEndPoint this holds the customized endpoints of API Call
         * @return [CustomMessage] of [RoStatusResponse]
         */
        override suspend fun updateROStateRequest(customEndPoint: CustomEndPoint): CustomMessage<RoStatusResponse> {
            retrofitManager.sendRequest(customEndPoint).also {
                return if (it != null && it.isSuccessful) {
                    val data = Gson().fromJson<RoStatusResponse>(it.body().toString())
                    val resp = CustomMessage<RoStatusResponse>(Status.Success)
                    resp.setResponseData(data)
                    resp
                } else {
                    networkError(it?.errorBody(), it?.code())
                }
            }
        }

        /**
         * This function is to call the API for getting the RO history of the provided vehicle using retrofit service
         *
         * @param customEndPoint this holds the customized endpoints of API Call
         * @return [CustomMessage] with [List] of [RoEventHistoryResponse]
         */
        override suspend fun getRemoteOperationHistory(customEndPoint: CustomEndPoint): CustomMessage<List<RoEventHistoryResponse>> {
            retrofitManager.sendRequest(customEndPoint).also {
                return if (it != null && it.isSuccessful) {
                    val data = Gson().fromJson<List<RoEventHistoryResponse>>(it.body().toString())
                    val resp = CustomMessage<List<RoEventHistoryResponse>>(Status.Success)
                    resp.setResponseData(data)
                    resp
                } else {
                    networkError(it?.errorBody(), it?.code())
                }
            }
        }

        /**
         * This function is to call the API for checking the RO state which is updated using retrofit service
         *
         * @param customEndPoint this holds the customized endpoints of API Call
         * @return [CustomMessage] of [RoEventHistoryResponse]
         */
        override suspend fun checkRemoteOperationRequestStatus(customEndPoint: CustomEndPoint): CustomMessage<RoEventHistoryResponse> {
            retrofitManager.sendRequest(customEndPoint).also {
                return if (it != null && it.isSuccessful) {
                    val data = Gson().fromJson<RoEventHistoryResponse>(it.body().toString())
                    val resp = CustomMessage<RoEventHistoryResponse>(Status.Success)
                    resp.setResponseData(data)
                    resp
                } else {
                    networkError(it?.errorBody(), it?.code())
                }
            }
        }
    }

/**
 * RoRepositoryInterface is implemented by RoRepository to override the functions available for RO
 *
 */
interface RoRepositoryInterface {
    /**
     * represents to update the RO state
     *
     * @param customEndPoint holds the end point of API
     * @return [CustomMessage] of [RoStatusResponse] value as response
     */
    suspend fun updateROStateRequest(customEndPoint: CustomEndPoint): CustomMessage<RoStatusResponse>

    /**
     * represents to get the RO history
     *
     * @param customEndPoint holds the end point of API
     * @return [CustomMessage] with [List] of [RoEventHistoryResponse] value as response
     */
    suspend fun getRemoteOperationHistory(customEndPoint: CustomEndPoint): CustomMessage<List<RoEventHistoryResponse>>

    /**
     * represents to check the status of RO request
     *
     * @param customEndPoint holds the end point of API
     * @return [CustomMessage] of [RoEventHistoryResponse] value as response
     */
    suspend fun checkRemoteOperationRequestStatus(customEndPoint: CustomEndPoint): CustomMessage<RoEventHistoryResponse>
}
