package org.eclipse.sdkapp

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import org.eclipse.ecsp.CustomEndPoint
import org.eclipse.ecsp.network.networkmanager.IRetrofitManager
import org.eclipse.ecsp.userservice.model.TokenData
import org.eclipse.ecsp.vehicleservice.endpoint.VehicleEndPoint
import org.eclipse.ecsp.vehicleservice.model.AssociatedDevice
import org.eclipse.ecsp.vehicleservice.model.DeviceAssociationListData
import org.eclipse.ecsp.vehicleservice.model.DeviceVerificationData
import org.eclipse.ecsp.vehicleservice.model.MetaData
import org.eclipse.ecsp.vehicleservice.model.TerminateDeviceData
import org.eclipse.ecsp.vehicleservice.model.deviceassociation.AssociatedDeviceInfo
import org.eclipse.ecsp.vehicleservice.model.deviceassociation.AssociationData
import org.eclipse.ecsp.vehicleservice.model.vehicleprofile.PostVehicleAttributeData
import org.eclipse.ecsp.vehicleservice.model.vehicleprofile.UserData
import org.eclipse.ecsp.vehicleservice.model.vehicleprofile.VehicleAttributeData
import org.eclipse.ecsp.vehicleservice.model.vehicleprofile.VehicleAttributeDetail
import org.eclipse.ecsp.vehicleservice.model.vehicleprofile.VehicleDetailData
import org.eclipse.ecsp.vehicleservice.model.vehicleprofile.VehicleProfileData
import org.eclipse.ecsp.vehicleservice.repository.VehicleRepository
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
class VehicleModuleTestClass {
    private lateinit var activity: VehicleActivityModule

    @Mock
    private lateinit var retrofitManager: IRetrofitManager

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        activity = Robolectric.buildActivity(VehicleActivityModule::class.java).create().get()
        activity.configureEnvironment()
    }

    @Test
    fun associatedDeviceList_success_test_case() {
        val endPoint = VehicleEndPoint.DeviceAssociationList

        val vehicleRepository = spy(VehicleRepository())
        vehicleRepository.retrofitManager = retrofitManager
        val associatedDevice = AssociatedDevice(
            0,
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            MetaData("")
        )

        val response = DeviceAssociationListData("", "", arrayListOf(associatedDevice))
        val jsonElement = JsonParser.parseString(Gson().toJson(response).toString())
        val responseJsonElement = Response.success(200, jsonElement)

        runBlocking {
            `when`(vehicleRepository.retrofitManager.sendRequest(endPoint)).thenReturn(
                responseJsonElement
            )
            activity.associatedDeviceList()
            vehicleRepository.associatedDeviceList {}
            val result = vehicleRepository.retrofitManager.sendRequest(endPoint)
            Assert.assertEquals(
                responseJsonElement, result
            )
        }
    }

    @Test
    fun associatedDeviceList_failure_test_case() {
        val endPoint = VehicleEndPoint.DeviceAssociationList

        val vehicleRepository = spy(VehicleRepository())
        vehicleRepository.retrofitManager = retrofitManager
        val responseJsonElement =
            Response.error<JsonElement>(401, "Unauthorized".toResponseBody(null))

        runBlocking {
            `when`(vehicleRepository.retrofitManager.sendRequest(endPoint)).thenReturn(
                responseJsonElement
            )
            activity.associatedDeviceList()
            vehicleRepository.associatedDeviceList {}
            val result = vehicleRepository.retrofitManager.sendRequest(endPoint)
            Assert.assertEquals(
                responseJsonElement.code(), result?.code()
            )
        }
    }


    @Test
    fun verifyDeviceImei_failure_test_case() {
        val endPoint = VehicleEndPoint.VerifyDevice
        val customEndPoint = CustomEndPoint(
            endPoint.baseUrl ?: "",
            endPoint.path + "imeiNumber",
            endPoint.method,
            endPoint.header,
            endPoint.body
        )
        val vehicleRepository = spy(VehicleRepository())
        vehicleRepository.retrofitManager = retrofitManager
        val responseJsonElement =
            Response.error<JsonElement>(401, "Unauthorized".toResponseBody(null))

        runBlocking {
            `when`(vehicleRepository.retrofitManager.sendRequest(customEndPoint)).thenReturn(
                responseJsonElement
            )
            activity.verifyDeviceImei()
            vehicleRepository.verifyDeviceImei(customEndPoint) {}
            val result = vehicleRepository.retrofitManager.sendRequest(customEndPoint)
            Assert.assertEquals(
                responseJsonElement.code(), result?.code()
            )
        }
    }


    @Test
    fun verifyDeviceImei_success_test_case() {
        val endPoint = VehicleEndPoint.VerifyDevice
        val customEndPoint = CustomEndPoint(
            endPoint.baseUrl ?: "",
            endPoint.path + "imeiNumber",
            endPoint.method,
            endPoint.header,
            endPoint.body
        )
        val vehicleRepository = spy(VehicleRepository())
        vehicleRepository.retrofitManager = retrofitManager
        val deviceVerificationData = DeviceVerificationData(
            DeviceVerificationData.DeviceVerificationMessages(""),
            DeviceVerificationData.VerificationStatus(false)
        )
        val jsonElement = JsonParser.parseString(Gson().toJson(deviceVerificationData))
        val responseJsonElement = Response.success(200, jsonElement)

        runBlocking {
            `when`(vehicleRepository.retrofitManager.sendRequest(customEndPoint)).thenReturn(
                responseJsonElement
            )
            activity.verifyDeviceImei()
            vehicleRepository.verifyDeviceImei(customEndPoint) {}
            val result = vehicleRepository.retrofitManager.sendRequest(customEndPoint)
            Assert.assertEquals(
                responseJsonElement, result
            )
        }
    }


    @Test
    fun associateDevice_failure_test_case() {
        val endPoint = VehicleEndPoint.DeviceAssociation
        val customEndPoint = CustomEndPoint(
            endPoint.baseUrl ?: "",
            endPoint.path,
            endPoint.method,
            endPoint.header,
            AssociationData(mImei = "imeiNumber")
        )
        val vehicleRepository = spy(VehicleRepository())
        vehicleRepository.retrofitManager = retrofitManager
        val responseJsonElement =
            Response.error<JsonElement>(401, "Unauthorized".toResponseBody(null))

        runBlocking {
            `when`(vehicleRepository.retrofitManager.sendRequest(customEndPoint)).thenReturn(
                responseJsonElement
            )
            TokenData("","","","","",0)
            activity.associateDevice()
            vehicleRepository.associateDevice(customEndPoint) {}
            val result = vehicleRepository.retrofitManager.sendRequest(customEndPoint)
            Assert.assertEquals(
                responseJsonElement.code(), result?.code()
            )
        }
    }


    @Test
    fun associateDevice_success_test_case() {
        val endPoint = VehicleEndPoint.DeviceAssociation
        val customEndPoint = CustomEndPoint(
            endPoint.baseUrl ?: "",
            endPoint.path,
            endPoint.method,
            endPoint.header,
            AssociationData(mImei = "imeiNumber")
        )
        val vehicleRepository = spy(VehicleRepository())
        vehicleRepository.retrofitManager = retrofitManager
        val associatedDeviceInfo =
            AssociatedDeviceInfo("", "", AssociatedDeviceInfo.AssociationInfo(0, ""))
        val jsonElement = JsonParser.parseString(Gson().toJson(associatedDeviceInfo).toString())
        val responseJsonElement = Response.success(200, jsonElement)

        runBlocking {
            `when`(vehicleRepository.retrofitManager.sendRequest(customEndPoint)).thenReturn(
                responseJsonElement
            )
            activity.associateDevice()
            vehicleRepository.associateDevice(customEndPoint) {}
            val result = vehicleRepository.retrofitManager.sendRequest(customEndPoint)
            Assert.assertEquals(
                responseJsonElement, result
            )
        }
    }

    @Test
    fun getVehicleProfile_failure_test_case() {
        val endPoint = VehicleEndPoint.VehicleProfile
        val customEndPoint = CustomEndPoint(
            endPoint.baseUrl ?: "",
            endPoint.path + "deviceId",
            endPoint.method,
            endPoint.header,
            endPoint.body
        )
        val vehicleRepository = spy(VehicleRepository())
        vehicleRepository.retrofitManager = retrofitManager
        val responseJsonElement =
            Response.error<JsonElement>(401, "Unauthorized".toResponseBody(null))

        runBlocking {
            `when`(vehicleRepository.retrofitManager.sendRequest(customEndPoint)).thenReturn(
                responseJsonElement
            )
            activity.getVehicleProfile()
            vehicleRepository.getVehicleProfile(customEndPoint) {}
            val result = vehicleRepository.retrofitManager.sendRequest(customEndPoint)
            Assert.assertEquals(
                responseJsonElement.code(), result?.code()
            )
        }
    }

    @Test
    fun getVehicleProfile_success_test_case() {
        val endPoint = VehicleEndPoint.VehicleProfile
        val customEndPoint = CustomEndPoint(
            endPoint.baseUrl ?: "",
            endPoint.path + "deviceId",
            endPoint.method,
            endPoint.header,
            endPoint.body
        )
        val vehicleRepository = spy(VehicleRepository())
        vehicleRepository.retrofitManager = retrofitManager
        val vehicleProfileData = VehicleProfileData(
            "",
            arrayListOf(
                VehicleDetailData(
                    arrayListOf(UserData("", "")),
                    VehicleAttributeData(
                        "", "", "", "",
                        "", "", "", "", "",
                        "", "", "", ""
                    ),
                    "",
                    "",
                    false
                )
            )
        )
        val jsonElement = JsonParser.parseString(Gson().toJson(vehicleProfileData).toString())
        val responseJsonElement = Response.success(200, jsonElement)

        runBlocking {
            `when`(vehicleRepository.retrofitManager.sendRequest(customEndPoint)).thenReturn(
                responseJsonElement
            )
            activity.getVehicleProfile()
            vehicleRepository.getVehicleProfile(customEndPoint) {}
            val result = vehicleRepository.retrofitManager.sendRequest(customEndPoint)
            Assert.assertEquals(
                responseJsonElement, result
            )
        }
    }

    @Test
    fun updateVehicleProfile_failure_test_case() {
        val endPoint = VehicleEndPoint.UpdateVehicleProfile
        val customEndPoint = CustomEndPoint(
            endPoint.baseUrl ?: "",
            endPoint.path + "deviceId",
            endPoint.method,
            endPoint.header,
            PostVehicleAttributeData(VehicleAttributeDetail(("")))
        )
        val vehicleRepository = spy(VehicleRepository())
        vehicleRepository.retrofitManager = retrofitManager
        val responseJsonElement =
            Response.error<JsonElement>(401, "Unauthorized".toResponseBody(null))

        runBlocking {
            `when`(vehicleRepository.retrofitManager.sendRequest(customEndPoint)).thenReturn(
                responseJsonElement
            )
            activity.updateVehicleProfile()
            vehicleRepository.updateVehicleProfile(customEndPoint) {}
            val result = vehicleRepository.retrofitManager.sendRequest(customEndPoint)
            Assert.assertEquals(
                responseJsonElement.code(), result?.code()
            )
        }
    }

    @Test
    fun updateVehicleProfile_success_test_case() {
        val endPoint = VehicleEndPoint.UpdateVehicleProfile
        val customEndPoint = CustomEndPoint(
            endPoint.baseUrl ?: "",
            endPoint.path + "deviceId",
            endPoint.method,
            endPoint.header,
            PostVehicleAttributeData(VehicleAttributeDetail(("")))
        )
        val vehicleRepository = spy(VehicleRepository())
        vehicleRepository.retrofitManager = retrofitManager
        val jsonElement = JsonParser.parseString("")
        val responseJsonElement = Response.success(200, jsonElement)

        runBlocking {
            `when`(vehicleRepository.retrofitManager.sendRequest(customEndPoint)).thenReturn(
                responseJsonElement
            )
            activity.updateVehicleProfile()
            vehicleRepository.updateVehicleProfile(customEndPoint) {}
            val result = vehicleRepository.retrofitManager.sendRequest(customEndPoint)
            Assert.assertEquals(
                responseJsonElement, result
            )
        }
    }

    @Test
    fun terminateVehicle_failure_test_case() {
        val endPoint = VehicleEndPoint.TerminateDevice
        val customEndPoint = CustomEndPoint(
            endPoint.baseUrl ?: "",
            endPoint.path,
            endPoint.method,
            endPoint.header,
            TerminateDeviceData("", "", "")
        )
        val vehicleRepository = spy(VehicleRepository())
        vehicleRepository.retrofitManager = retrofitManager
        val responseJsonElement =
            Response.error<JsonElement>(401, "Unauthorized".toResponseBody(null))

        runBlocking {
            `when`(vehicleRepository.retrofitManager.sendRequest(customEndPoint)).thenReturn(
                responseJsonElement
            )
            activity.terminateDevice()
            vehicleRepository.terminateDevice(customEndPoint) {}
            val result = vehicleRepository.retrofitManager.sendRequest(customEndPoint)
            Assert.assertEquals(
                responseJsonElement.code(), result?.code()
            )
        }
    }

    @Test
    fun terminateVehicle_success_test_case() {
        val endPoint = VehicleEndPoint.TerminateDevice
        val customEndPoint = CustomEndPoint(
            endPoint.baseUrl ?: "",
            endPoint.path,
            endPoint.method,
            endPoint.header,
            TerminateDeviceData("", "", "")
        )
        val vehicleRepository = spy(VehicleRepository())
        vehicleRepository.retrofitManager = retrofitManager
        val jsonElement = JsonParser.parseString("")
        val responseJsonElement = Response.success(200, jsonElement)

        runBlocking {
            `when`(vehicleRepository.retrofitManager.sendRequest(customEndPoint)).thenReturn(
                responseJsonElement
            )
            activity.terminateDevice()
            vehicleRepository.terminateDevice(customEndPoint) {}
            val result = vehicleRepository.retrofitManager.sendRequest(customEndPoint)
            Assert.assertEquals(
                responseJsonElement, result
            )
        }
    }
}