package com.harman.androidvehicleconnectsdk.notificationservice.repository

import com.google.gson.Gson
import com.harman.androidvehicleconnectsdk.CustomEndPoint
import com.harman.androidvehicleconnectsdk.helper.AppManager
import com.harman.androidvehicleconnectsdk.helper.fromJson
import com.harman.androidvehicleconnectsdk.helper.networkError
import com.harman.androidvehicleconnectsdk.helper.networkResponse
import com.harman.androidvehicleconnectsdk.helper.response.CustomMessage
import com.harman.androidvehicleconnectsdk.network.networkmanager.IRetrofitManager
import com.harman.androidvehicleconnectsdk.notificationservice.model.AlertAnalysisData
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
@Singleton
class NotificationRepository @Inject constructor() : NotificationRepoInterface {

    @Inject
    lateinit var retrofitManager: IRetrofitManager
    init {
        AppManager.getAppComponent().inject(this)
    }

    /**
     * This function is to call the API for updating the Notification config using retrofit service
     * @param customEndPoint this holds the customized endpoints of API Call
     * @param customMessage this is the call back function to pass the response value
    */
    override suspend fun updateNotificationConfig(
        customEndPoint: CustomEndPoint,
        customMessage: (CustomMessage<String>) -> Unit
    ) {
        retrofitManager.sendRequest(customEndPoint).also {
            if (it != null && it.isSuccessful) {
                customMessage(networkResponse("Successfully updated the notification configuration data"))
            } else {
                customMessage(networkError(it?.errorBody(), it?.code()))
            }
        }
    }

    /**
     * This function is to call the API for getting the Alert Notification history using retrofit service
     * @param customEndPoint this holds the customized endpoints of API Call
     * @param customMessage this is the call back function to pass the response value
     */
    override suspend fun notificationAlertHistory(
        customEndPoint: CustomEndPoint,
        customMessage: (CustomMessage<AlertAnalysisData>) -> Unit
    ) {
        retrofitManager.sendRequest(customEndPoint).also {
            if (it != null && it.isSuccessful) {
                val data = Gson().fromJson<AlertAnalysisData>(it.body().toString())
                customMessage(networkResponse(data))
            } else {
                customMessage(networkError(it?.errorBody(), it?.code()))
            }
        }
    }
}

interface NotificationRepoInterface {
    suspend fun updateNotificationConfig(
        customEndPoint: CustomEndPoint,
        customMessage: (CustomMessage<String>) -> Unit
    )

    suspend fun notificationAlertHistory(
        customEndPoint: CustomEndPoint,
        customMessage: (CustomMessage<AlertAnalysisData>) -> Unit
    )
}