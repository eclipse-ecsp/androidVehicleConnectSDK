package com.harman.sdkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.harman.androidvehicleconnectsdk.appauth.AuthInterface
import com.harman.androidvehicleconnectsdk.environment.Environment
import com.harman.androidvehicleconnectsdk.environment.EnvironmentManager
import com.harman.androidvehicleconnectsdk.helper.AppManager
import com.harman.androidvehicleconnectsdk.roservice.model.RemoteOperationState
import com.harman.androidvehicleconnectsdk.roservice.service.RoServiceInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
class RoActivityModule : ComponentActivity(), AuthInterface {
    private val roServiceInterface: RoServiceInterface by lazy {
        RoServiceInterface.roServiceInterface()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.initialize(this.application)
    }

    fun configureEnvironment() {
        val envData = Environment(
            "ENV", "dummy", "dummy", BASE_URL,
            BASE_URL, BASE_URL, BASE_URL, "auth://authorization",
            arrayListOf("scope1", "scope2","scope3", "scope4", "scope5", "scope5" )
        )
        EnvironmentManager.configure(envData)
    }

    fun getRoHistory() {
        CoroutineScope(Dispatchers.IO).launch {
            roServiceInterface.getRemoteOperationHistory(
                "new_dummy_user@yopmail.com",
                "dummy"
            ) {

            }
        }
    }

    fun checkRoRequest() {
        CoroutineScope(Dispatchers.IO).launch {
            roServiceInterface.checkRemoteOperationRequestStatus(
                "new_dummy_user@yopmail.com",
                "dummy",
                "requestId"
            ) {

            }
        }
    }

    fun updateRoState() {
        CoroutineScope(Dispatchers.IO).launch {
            roServiceInterface.updateROStateRequest(
                "new_dummy_user@yopmail.com",
                "dummy",
                8, 10, RemoteOperationState.AlarmOff
            ) {

            }
        }
    }

    override var accessToken: String = "Token"
    override var refreshToken: String = "test_refresh_token"
    override var tokenType: String = "Bearer"
    override var scope: String = "test_scope"
    override var accessTokenExpirationDate: Long = 0L
    override var additionalParameters: HashMap<Any, Any> = hashMapOf()
}