package org.eclipse.sdkapp

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import org.eclipse.ecsp.CustomEndPoint
import org.eclipse.ecsp.helper.Constant
import org.eclipse.ecsp.network.networkmanager.IRetrofitManager
import org.eclipse.ecsp.notificationservice.endpoint.NotificationEndPoint
import org.eclipse.ecsp.notificationservice.model.AlertAnalysisData
import org.eclipse.ecsp.notificationservice.model.AlertData
import org.eclipse.ecsp.notificationservice.model.AlertDataPropertiesData
import org.eclipse.ecsp.notificationservice.model.DataItem
import org.eclipse.ecsp.notificationservice.model.NotificationConfigData
import org.eclipse.ecsp.notificationservice.model.PayLoadData
import org.eclipse.ecsp.notificationservice.repository.NotificationRepository
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.powermock.api.mockito.PowerMockito
import org.powermock.api.mockito.PowerMockito.`when`
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

/**
 * Copyright (c) 2023-24 Harman International
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
@RunWith(RobolectricTestRunner::class)
class NotificationModuleTestCase {
    private lateinit var activity: NotificationActivityModule

    @Mock
    private lateinit var retrofitManager: IRetrofitManager

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        activity = Robolectric.buildActivity(NotificationActivityModule::class.java).create().get()
        activity.configureEnvironment()
    }

    @Test
    fun updateNotificationConfig_success_test_case() {
        val endPoint = NotificationEndPoint.NotificationConfig
        val customEndPoint = org.eclipse.ecsp.CustomEndPoint(
            endPoint.baseUrl,
            "${
                endPoint.path?.replace(Constant.USER_ID, "userId")
                    ?.replace(Constant.VEHICLE_ID, "vehicleId")?.replace(
                        Constant.CONTACT_ID, "contactId"
                    )
            }",
            endPoint.method,
            endPoint.header,
            arrayListOf<NotificationConfigData>()
        )
        val notificationRepository = PowerMockito.spy(NotificationRepository())
        notificationRepository.retrofitManager = retrofitManager
        val jsonElement = JsonParser.parseString("")
        val responseJsonElement = Response.success(200, jsonElement)

        runBlocking {
            `when`(notificationRepository.retrofitManager.sendRequest(customEndPoint)).thenReturn(
                responseJsonElement
            )
            activity.updateNotificationConfig()
            notificationRepository.updateNotificationConfig(customEndPoint) {}
            val result = notificationRepository.retrofitManager.sendRequest(customEndPoint)
            Assert.assertEquals(
                responseJsonElement, result
            )
        }
    }


    @Test
    fun updateNotificationConfig_failure_test_case() {
        val endPoint = NotificationEndPoint.NotificationConfig
        val customEndPoint = org.eclipse.ecsp.CustomEndPoint(
            endPoint.baseUrl,
            endPoint.path,
            endPoint.method,
            endPoint.header,
            arrayListOf<NotificationConfigData>()
        )
        val notificationRepository = PowerMockito.spy(NotificationRepository())
        notificationRepository.retrofitManager = retrofitManager
        val responseJsonElement =
            Response.error<JsonElement>(500, ResponseBody.create(null, "Server error"))

        runBlocking {
            `when`(notificationRepository.retrofitManager.sendRequest(customEndPoint)).thenReturn(
                responseJsonElement
            )
            activity.updateNotificationConfig()
            notificationRepository.updateNotificationConfig(customEndPoint) {}
            val result = notificationRepository.retrofitManager.sendRequest(customEndPoint)
            Assert.assertEquals(
                responseJsonElement.code(), result?.code()
            )
        }
    }

    @Test
    fun notificationAlertHistory_success_test_case() {
        val endPoint = NotificationEndPoint.NotificationAlert
        val customEndPoint = org.eclipse.ecsp.CustomEndPoint(
            endPoint.baseUrl ?: "",
            endPoint.path, endPoint.method, endPoint.header, endPoint.body
        )

        val notificationRepository = PowerMockito.spy(NotificationRepository())
        notificationRepository.retrofitManager = retrofitManager
        val alertAnalysisData = AlertAnalysisData(
            AlertAnalysisData.AlertPaginationData(
                0, 0,
                AlertAnalysisData.AlertPaginationTotal(1, 1)
            ),
            arrayListOf(
                AlertData(
                    "",
                    0,
                    "",
                    "",
                    "",
                    PayLoadData(
                        "",
                        DataItem(
                            AlertDataPropertiesData(0.0, 0.0, "", "", ""),
                            0.0,
                            0.0,
                            "",
                            "",
                            ""
                        ),
                        "",
                        0,
                        0,
                        0,
                        ""
                    ),
                    "",
                    read = false,
                    deleted = false
                )
            )
        )
        val jsonElement = JsonParser.parseString(Gson().toJson(alertAnalysisData).toString())
        val responseJsonElement = Response.success(200, jsonElement)

        runBlocking {
            `when`(notificationRepository.retrofitManager.sendRequest(customEndPoint))
                .thenReturn(responseJsonElement)
            activity.notificationAlertHistory()
            notificationRepository.notificationAlertHistory(customEndPoint) {}
            val result = notificationRepository.retrofitManager.sendRequest(customEndPoint)
            Assert.assertEquals(
                responseJsonElement, result
            )
        }
    }

    @Test
    fun notificationAlertHistory_failure_test_case() {
        val endPoint = NotificationEndPoint.NotificationAlert
        val customEndPoint = org.eclipse.ecsp.CustomEndPoint(
            endPoint.baseUrl ?: "",
            endPoint.path, endPoint.method, endPoint.header, endPoint.body
        )

        val notificationRepository = PowerMockito.spy(NotificationRepository())
        notificationRepository.retrofitManager = retrofitManager
        val responseJsonElement =
            Response.error<JsonElement>(403, ResponseBody.create(null, "Forbidden"))

        runBlocking {
            `when`(notificationRepository.retrofitManager.sendRequest(customEndPoint)).thenReturn(
                responseJsonElement
            )
            activity.notificationAlertHistory()
            notificationRepository.notificationAlertHistory(customEndPoint) {}
            val result = notificationRepository.retrofitManager.sendRequest(customEndPoint)
            Assert.assertEquals(
                responseJsonElement.code(), result?.code()
            )
        }
    }
}