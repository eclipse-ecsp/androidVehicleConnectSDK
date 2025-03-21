package org.eclipse.sdkapp

import android.content.Intent
import org.eclipse.ecsp.environment.Environment
import org.eclipse.ecsp.environment.EnvironmentManager
import org.eclipse.ecsp.helper.Constant
import org.eclipse.ecsp.ui.UiApplication
import org.eclipse.sdkapp.TestConstant.BASE_URL
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.powermock.api.mockito.PowerMockito.spy
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

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
class AppAuthProviderTestClass {
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        val envData = Environment(
            "TEST_ENV", "DUMMY", "dummy", BASE_URL,
            BASE_URL, BASE_URL, BASE_URL, "auth://authorization",
            arrayListOf("scope1", "scope2","scope3", "scope4", "scope5", "scope5" )
        )
        envData.toString()
        EnvironmentManager.configure(envData)
    }

    @Test
    fun signIn_test_case() {
        val intent = Intent().putExtra(Constant.ACTION_KEY, Constant.SIGN_IN)
        spy(Robolectric.buildActivity(UiApplication::class.java, intent).create()).get()
    }

    @Test
    fun signIn_env_not_configured_test_case() {
        val envData = Environment(
            "ENV", null, null, BASE_URL,
            BASE_URL, BASE_URL, BASE_URL, "auth://authorization",
            arrayListOf("scope1", "scope2","scope3", "scope4", "scope5", "scope5" )
        )
        EnvironmentManager.configure(envData)
        val intent = Intent().putExtra(Constant.ACTION_KEY, Constant.SIGN_IN)
        spy(Robolectric.buildActivity(UiApplication::class.java, intent).create()).get()
    }

    @Test
    fun signUp_TestCase(){
        val intent = Intent().putExtra(Constant.ACTION_KEY, Constant.SIGN_UP)
        spy(Robolectric.buildActivity(UiApplication::class.java, intent).create())
    }

    @Test
    fun sign_Out_testCase(){
        val intent = Intent().putExtra(Constant.ACTION_KEY, Constant.SIGN_OUT)
        spy(Robolectric.buildActivity(UiApplication::class.java, intent).create())
    }

    @Test
    fun invalid_action_type_test_case(){
        val intent = Intent().putExtra(Constant.ACTION_KEY, "INVALID")
        spy(Robolectric.buildActivity(UiApplication::class.java, intent).create())
    }

    @Test
    fun invalid_intent_type_test_case(){
        spy(Robolectric.buildActivity(UiApplication::class.java).create())
    }


}