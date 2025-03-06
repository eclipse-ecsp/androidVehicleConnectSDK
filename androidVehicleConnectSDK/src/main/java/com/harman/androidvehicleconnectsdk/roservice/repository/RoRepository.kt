package com.harman.androidvehicleconnectsdk.roservice.repository

import com.google.gson.Gson
import com.harman.androidvehicleconnectsdk.CustomEndPoint
import com.harman.androidvehicleconnectsdk.helper.AppManager
import com.harman.androidvehicleconnectsdk.helper.fromJson
import com.harman.androidvehicleconnectsdk.helper.networkError
import com.harman.androidvehicleconnectsdk.helper.networkResponse
import com.harman.androidvehicleconnectsdk.helper.response.CustomMessage
import com.harman.androidvehicleconnectsdk.network.networkmanager.IRetrofitManager
import com.harman.androidvehicleconnectsdk.roservice.model.RoEventHistoryResponse
import com.harman.androidvehicleconnectsdk.roservice.model.RoStatusResponse
import javax.inject.Inject
import javax.inject.Singleton

/**
********************************************************************************
* COPYRIGHT (c) 2024 Harman International Industries, Inc                  *
*                                                                              *
* All rights reserved                                                          *
*                                                                              *
* This software embodies materials and concepts which are                      *
* confidential to Harman International Industries, Inc. and is                 *
* made available solely pursuant to the terms of a written license             *
* agreement with Harman International Industries, Inc.                         *
*                                                                              *
* Designed and Developed by Harman International Industries, Inc.              *
*------------------------------------------------------------------------------*
* MODULE OR UNIT: <<name of the component or module>>                          *
********************************************************************************
*/

/**
 * RoRepository is used to call the API for remote operation feature
 * This have the functions to update RO state, get history of RO state and check status of RO state
 *
 */
@Singleton
class RoRepository @Inject constructor() : RoRepositoryInterface {
    @Inject
    lateinit var retrofitManager: IRetrofitManager

    init {
        AppManager.getAppComponent().inject(this)
    }

    /**
     * This function is to call the API for update the RO state using retrofit service
     * @param customEndPoint this holds the customized endpoints of API Call
     * @param customMessage this is the call back function to pass the response value
     */
    override suspend fun updateROStateRequest(
        customEndPoint: CustomEndPoint,
        customMessage: (CustomMessage<RoStatusResponse>) -> Unit
    ) {
        retrofitManager.sendRequest(customEndPoint).also {
            if (it != null && it.isSuccessful) {
                val data = Gson().fromJson<RoStatusResponse>(it.body().toString())
                customMessage(networkResponse(data))
            } else {
                customMessage(networkError(it?.errorBody(), it?.code()))
            }
        }
    }

    /**
     * This function is to call the API for getting the RO history of the provided vehicle using retrofit service
     *
     * @param customEndPoint this holds the customized endpoints of API Call
     * @param customMessage this is the call back function to pass the response value
     */
    override suspend fun getRemoteOperationHistory(
        customEndPoint: CustomEndPoint,
        customMessage: (CustomMessage<List<RoEventHistoryResponse>>) -> Unit
    ) {
        retrofitManager.sendRequest(customEndPoint).also {
            if (it != null && it.isSuccessful) {
                val data = Gson().fromJson<List<RoEventHistoryResponse>>(it.body().toString())
                customMessage(networkResponse(data))
            } else {
                customMessage(networkError(it?.errorBody(), it?.code()))
            }
        }
    }

    /**
     * This function is to call the API for checking the RO state which is updated using retrofit service
     *
     * @param customEndPoint this holds the customized endpoints of API Call
     * @param customMessage this is the call back function to pass the response value
     */
    override suspend fun checkRemoteOperationRequestStatus(
        customEndPoint: CustomEndPoint,
        customMessage: (CustomMessage<RoEventHistoryResponse>) -> Unit
    ) {
        retrofitManager.sendRequest(customEndPoint).also {
            if (it != null && it.isSuccessful) {
                val data = Gson().fromJson<RoEventHistoryResponse>(it.body().toString())
                customMessage(networkResponse(data))
            } else {
                customMessage(networkError(it?.errorBody(), it?.code()))
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
     * @param customMessage higher order function to emit the CustomMessage<AlertAnalysisData> value as response
     */
    suspend fun updateROStateRequest(
        customEndPoint: CustomEndPoint,
        customMessage: (CustomMessage<RoStatusResponse>) -> Unit
    )

    /**
     * represents to get the RO history
     *
     * @param customEndPoint holds the end point of API
     * @param customMessage higher order function to emit the CustomMessage<List<RoEventHistoryResponse>> value as response
     */
    suspend fun getRemoteOperationHistory(
        customEndPoint: CustomEndPoint,
        customMessage: (CustomMessage<List<RoEventHistoryResponse>>) -> Unit
    )

    /**
     * represents to check the status of RO request
     *
     * @param customEndPoint holds the end point of API
     * @param customMessage higher order function to emit the CustomMessage<RoEventHistoryResponse> value as response
     */
    suspend fun checkRemoteOperationRequestStatus(
       customEndPoint: CustomEndPoint,
        customMessage: (CustomMessage<RoEventHistoryResponse>) -> Unit
    )
}