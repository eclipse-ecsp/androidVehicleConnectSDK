package org.eclipse.sdkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import org.eclipse.ecsp.appauth.AuthInterface
import org.eclipse.ecsp.environment.Environment
import org.eclipse.ecsp.environment.EnvironmentManager
import org.eclipse.ecsp.helper.AppManager
import org.eclipse.ecsp.notificationservice.model.ChannelData
import org.eclipse.ecsp.notificationservice.model.NotificationConfigData
import org.eclipse.ecsp.notificationservice.service.NotificationServiceInterface
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
class NotificationActivityModule : ComponentActivity(), AuthInterface {

    private val iNotificationServiceInterface: NotificationServiceInterface by lazy {
        NotificationServiceInterface.notificationServiceInterface()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.initialize(this.application)
    }

    fun configureEnvironment() {
        val envData = Environment(
            "ENV", DUMMY_VALUE, DUMMY_VALUE, BASE_URL,
            BASE_URL, BASE_URL, BASE_URL, "auth://authorization",
            arrayListOf("scope1", "scope2","scope3", "scope4", "scope5", "scope5" )
        )
        EnvironmentManager.configure(envData)
    }

    fun updateNotificationConfig() {
        CoroutineScope(Dispatchers.IO).launch {
            val channelData = ChannelData(arrayListOf(), false, "", "","")
            val notificationConfigData = NotificationConfigData(arrayListOf(channelData), false, "")
            iNotificationServiceInterface.updateNotificationConfig("", "",
                "", arrayListOf(notificationConfigData))
        }
    }

    fun notificationAlertHistory() {
        CoroutineScope(Dispatchers.IO).launch {
            iNotificationServiceInterface.notificationAlertHistory(
                "",
                arrayListOf(),
                1L,
                1L,
                1,
                1,
                DUMMY_VALUE
            )
        }
    }

    override var accessToken: String = "Token"
    override var refreshToken: String = "test_refresh_token"
    override var tokenType: String = "Bearer"
    override var scope: String = "test_scope"
    override var accessTokenExpirationDate: Long = 0L
    override var additionalParameters: HashMap<Any, Any> = hashMapOf()
}