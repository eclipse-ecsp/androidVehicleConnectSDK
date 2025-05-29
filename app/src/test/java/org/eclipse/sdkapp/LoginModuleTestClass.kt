package org.eclipse.sdkapp

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import org.eclipse.ecsp.CustomEndPoint
import org.eclipse.ecsp.network.networkmanager.IRetrofitManager
import org.eclipse.ecsp.userservice.endpoint.UserEndPoint
import org.eclipse.ecsp.userservice.model.UserProfile
import org.eclipse.ecsp.userservice.model.UserProfileCollection
import org.eclipse.ecsp.userservice.repository.UserRepository
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
@RunWith(RobolectricTestRunner::class)
class LoginModuleTestClass {
    private lateinit var activity: LoginActivityModule

    @Mock
    private lateinit var retrofitManager: IRetrofitManager

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        activity = Robolectric.buildActivity(LoginActivityModule::class.java).create().get()
        activity.configureEnvironment()
    }

    @Test
    fun signIn_request_test_case() {
        activity.launchSignIn()
    }

    @Test
    fun signUp_request_test_case() {
        activity.launchSignUp()
    }

    @Test
    fun signOut_request_test_case() {
        activity.launchSignOut()
    }

    @Test
    fun fetchUserDataSuccessCase() {
        val userEndPoint = UserEndPoint.Profile
        val customEndPoint = CustomEndPoint(
            userEndPoint.baseUrl ?: "",
            userEndPoint.path,
            userEndPoint.method,
            userEndPoint.header,
            userEndPoint.body
        )
        val userRepository = spy(UserRepository())
        userRepository.retrofitManager = retrofitManager

        val userProfile = UserProfile("", "", "", "", arrayListOf(),
            "", "", "", "")
        val userProfileCollection = UserProfileCollection(arrayListOf(UserProfileCollection.Message("")), arrayListOf(userProfile))
        val dataClass = Gson().toJson(userProfileCollection).toString()

        val jsonElement = JsonParser.parseString(dataClass)
        val responseJsonElement = Response.success(200, jsonElement)

        runBlocking {
            `when`(userRepository.retrofitManager.sendRequest(customEndPoint)).thenReturn(
                responseJsonElement
            )
            activity.fetchUserData()
            userRepository.fetchUserProfile(customEndPoint)
            val result = userRepository.retrofitManager.sendRequest(customEndPoint)
            Assert.assertEquals(
                responseJsonElement, result
            )
        }
    }

    @Test
    fun fetchUserDataFailureCase() {
        activity.initializeSDK(false)
        val userEndPoint = UserEndPoint.Profile
        val customEndPoint = CustomEndPoint(
            userEndPoint.baseUrl ?: "",
            userEndPoint.path,
            userEndPoint.method,
            userEndPoint.header,
            userEndPoint.body
        )
        val userRepository = spy(UserRepository())
        userRepository.retrofitManager = retrofitManager
        val responseJsonElement =
            Response.error<JsonElement>(500, "Server error".toResponseBody(null))

        runBlocking {
            `when`(userRepository.retrofitManager.sendRequest(customEndPoint)).thenReturn(
                responseJsonElement
            )
            userRepository.fetchUserProfile(customEndPoint)
            val result = userRepository.retrofitManager.sendRequest(customEndPoint)
            Assert.assertNotEquals(200, result?.code())
        }
    }
}