package org.eclipse.sdkapp

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import org.eclipse.ecsp.CustomEndPoint
import org.eclipse.ecsp.helper.Constant
import org.eclipse.ecsp.network.networkmanager.IRetrofitManager
import org.eclipse.ecsp.roservice.endpoint.RoEndPoint
import org.eclipse.ecsp.roservice.model.RemoteOperationState
import org.eclipse.ecsp.roservice.model.RoEventData
import org.eclipse.ecsp.roservice.model.RoEventHistoryResponse
import org.eclipse.ecsp.roservice.model.RoRequestData
import org.eclipse.ecsp.roservice.model.RoStatusResponse
import org.eclipse.ecsp.roservice.repository.RoRepository
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.powermock.api.mockito.PowerMockito.spy
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
class ROModuleTestClass {
    private lateinit var activity: RoActivityModule

    @Mock
    private lateinit var retrofitManager: IRetrofitManager

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        activity = Robolectric.buildActivity(RoActivityModule::class.java).create().get()
        activity.configureEnvironment()
    }

    @Test
    fun getRoHistory_success_test_case() {
        val endPoint = RoEndPoint.RemoteOperationHistory
        val header = endPoint.header
        header?.put(TestConstant.HEADER_AUTHORIZATION, "Bearer ${TestConstant.SuccessTestToken}")
        val customEndPoint = CustomEndPoint(
            endPoint.baseUrl ?: "",
            "${
                (endPoint.path?.replace(Constant.USER_ID, "new_dummy_user@yopmail.com")
                    ?.replace(Constant.VEHICLE_ID, "dummy"))
            }history",
            endPoint.method,
            header,
            endPoint.body
        )

        val roResponse = RoEventHistoryResponse(
            RoEventData(
                "", "",
                0L, "", RoEventData.RoData("", "", 9)
            ), ""
        )

        val roRepository = spy(RoRepository())
        roRepository.retrofitManager = retrofitManager
        val jsonElement = JsonParser.parseString(Gson().toJson(arrayListOf(roResponse)).toString())
        val responseJsonElement = Response.success(200, jsonElement)

        runBlocking {
            `when`(roRepository.retrofitManager.sendRequest(customEndPoint)).thenReturn(
                responseJsonElement
            )
            activity.getRoHistory()
            roRepository.getRemoteOperationHistory(customEndPoint) {}
            val result = roRepository.retrofitManager.sendRequest(customEndPoint)
            Assert.assertEquals(
                responseJsonElement, result
            )
        }
    }

    @Test
    fun getRoHistory_failure_test_case() {
        val endPoint = RoEndPoint.RemoteOperationHistory
        val header = endPoint.header
        header?.put(TestConstant.HEADER_AUTHORIZATION, "Bearer ${TestConstant.FailureTestToken}")
        val customEndPoint = CustomEndPoint(
            endPoint.baseUrl ?: "",
            "${
                (endPoint.path?.replace(Constant.USER_ID, "new_dummy_user@yopmail.com")
                    ?.replace(Constant.VEHICLE_ID, "dummy"))
            }history",
            endPoint.method,
            header,
            endPoint.body
        )
        val roRepository = spy(RoRepository())
        roRepository.retrofitManager = retrofitManager
        val responseJsonElement =
            Response.error<JsonElement>(500, "Server error".toResponseBody(null))

        runBlocking {
            `when`(roRepository.retrofitManager.sendRequest(customEndPoint)).thenReturn(
                responseJsonElement
            )
            roRepository.getRemoteOperationHistory(customEndPoint) {}
            val result = roRepository.retrofitManager.sendRequest(customEndPoint)
            Assert.assertNotEquals(
                200, result?.code()
            )

        }
    }

    @Test
    fun checkRemoteOperationRequestStatus_success_test_case() {
        val endPoint = RoEndPoint.RemoteOperationRequestStatus
        val customEndPoint = CustomEndPoint(
            endPoint.baseUrl ?: "",
            "${
                (endPoint.path?.replace(Constant.USER_ID, "userId")
                    ?.replace(Constant.VEHICLE_ID, "vehicleId"))
            }requests/${"roRequestId"}",
            endPoint.method,
            endPoint.header,
            endPoint.body
        )
        val roRepository = spy(RoRepository())
        roRepository.retrofitManager = retrofitManager
        val jsonElement = JsonParser.parseString("{}")
        val responseJsonElement = Response.success(200, jsonElement)

        runBlocking {
            `when`(roRepository.retrofitManager.sendRequest(customEndPoint)).thenReturn(
                responseJsonElement
            )
            activity.checkRoRequest()
            roRepository.checkRemoteOperationRequestStatus(customEndPoint) {}
            val result = roRepository.retrofitManager.sendRequest(customEndPoint)
            Assert.assertEquals(
                responseJsonElement, result
            )
        }
    }

    @Test
    fun checkRemoteOperationRequestStatus_failure_test_case() {
        val endPoint = RoEndPoint.RemoteOperationRequestStatus
        val customEndPoint = CustomEndPoint(
            endPoint.baseUrl ?: "",
            "${
                (endPoint.path?.replace(Constant.USER_ID, "userId")
                    ?.replace(Constant.VEHICLE_ID, "vehicleId"))
            }requests/${"roRequestId"}",
            endPoint.method,
            endPoint.header,
            endPoint.body
        )
        val roRepository = spy(RoRepository())
        roRepository.retrofitManager = retrofitManager
        val responseJsonElement =
            Response.error<JsonElement>(500, "Server error".toResponseBody(null))

        runBlocking {
            `when`(roRepository.retrofitManager.sendRequest(customEndPoint)).thenReturn(
                responseJsonElement
            )
            roRepository.checkRemoteOperationRequestStatus(customEndPoint) {}
            val result = roRepository.retrofitManager.sendRequest(customEndPoint)
            Assert.assertNotEquals(
                200, result?.code()
            )
        }
    }

    @Test
    fun updateROStateRequest_success_test_case() {
        RemoteOperationState::class.sealedSubclasses.map {
            it.objectInstance as RemoteOperationState
        }.forEach { item ->
            val endPoint = RoEndPoint.UpdateRemoteOperation
            val customEndPoint = CustomEndPoint(
                endPoint.baseUrl ?: "",
                "${
                    (endPoint.path?.replace(Constant.USER_ID, "userId")
                        ?.replace(Constant.VEHICLE_ID, "vehicleId"))
                }${item}",
                endPoint.method,
                endPoint.header,
                RoRequestData("RemoteOperationState", 1, 1)
            )
            val roRepository = spy(RoRepository())
            roRepository.retrofitManager = retrofitManager
            val response = RoStatusResponse("message", "id")
            val jsonElement = JsonParser.parseString(Gson().toJson(response).toString())
            val responseJsonElement = Response.success(200, jsonElement)

            runBlocking {
                `when`(roRepository.retrofitManager.sendRequest(customEndPoint)).thenReturn(
                    responseJsonElement
                )
                activity.updateRoState()
                roRepository.updateROStateRequest(customEndPoint) {}
                val result = roRepository.retrofitManager.sendRequest(customEndPoint)
                Assert.assertEquals(
                    responseJsonElement, result
                )
            }
        }
    }

    @Test
    fun updateROStateRequest_failure_test_case() {
        val endPoint = RoEndPoint.UpdateRemoteOperation
        val customEndPoint = CustomEndPoint(
            endPoint.baseUrl ?: "",
            "${
                (endPoint.path?.replace(Constant.USER_ID, "userId")
                    ?.replace(Constant.VEHICLE_ID, "vehicleId"))
            }${RemoteOperationState.AlarmOff}",
            endPoint.method,
            endPoint.header,
            RoRequestData("RemoteOperationState", 1, 1)
        )

        val roRepository = spy(RoRepository())
        roRepository.retrofitManager = retrofitManager
        val responseJsonElement =
            Response.error<JsonElement>(400, "Bad request".toResponseBody(null))


        runBlocking {
            `when`(roRepository.retrofitManager.sendRequest(customEndPoint)).thenReturn(
                responseJsonElement
            )
            activity.updateRoState()
            roRepository.updateROStateRequest(customEndPoint) {}
            val result = roRepository.retrofitManager.sendRequest(customEndPoint)
            Assert.assertEquals(
                responseJsonElement.code(), result?.code()
            )
        }
    }
}