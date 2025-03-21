package org.eclipse.ecsp.helper
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
import android.app.Application
import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.eclipse.ecsp.appauth.AppAuthProvider
import org.eclipse.ecsp.appauth.AuthInterface
import org.eclipse.ecsp.appauth.AuthManager
import org.eclipse.ecsp.helper.Constant.CUSTOM_MESSAGE_VALUE
import org.eclipse.ecsp.helper.dicomponent.AppComponent
import org.eclipse.ecsp.helper.dicomponent.ContextModule
import org.eclipse.ecsp.helper.dicomponent.DaggerAppComponent
import org.eclipse.ecsp.helper.dicomponent.RepositoryModule
import org.eclipse.ecsp.helper.response.CustomMessage
import org.eclipse.ecsp.helper.sharedpref.AppDataStorage
import org.eclipse.ecsp.network.debugprint.DebugPrint
import org.eclipse.ecsp.network.debugprint.DebugPrintLogger

/**
 * AppManager is a singleton class which have all initial configurations and state needed for SDK
 * Used by the SDK consuming application
 */
object AppManager {
    private lateinit var appComponent: AppComponent
    internal var isRefreshTokenFailed = MutableLiveData<Boolean>()

    /**
     * This function used to initiate the SDK main components
     *
     * @param application represents the instance of SDK consuming application
     * Used to set the Dagger dependency injection and instance of SharedPreference
     * using  application_context shared by the application
     * @param enableLog Boolean value to enable/disable the SDK logs, by default it is true
     */
    fun initialize(
        application: Application,
        enableLog: Boolean = true,
    ) {
        appComponent =
            DaggerAppComponent.builder()
                .contextModule(ContextModule(application))
                .repositoryModule(RepositoryModule())
                .build()
        AppDataStorage.getInstance(application)
        if (enableLog) {
            DebugPrint.setLogger(DebugPrintLogger())
        }
        initializeAuthProvider(org.eclipse.ecsp.appauth.AppAuthProvider(application))
    }

    /**
     * This is to get the Dagger application component instance
     */
    internal fun getAppComponent(): AppComponent {
        return appComponent
    }

    /**
     * This function is to initiate the auth provider for login process
     *
     *@param authInterface represents the instance of AuthInterface
     * implemented by application
     */
    fun initializeAuthProvider(authInterface: AuthInterface) {
        AuthManager.sharedInterface(authInterface)
    }

    /**
     * * This function is to check the first time login
     *
     * @return this function returns Boolean value - is SDK login flow is completed or not
     */
    fun isLoggedIn(): Boolean = AppDataStorage.getAppPrefInstance()?.isLoggedIn ?: false

    /**
     * This function is to parse the authenticated response
     *
     * @param intent holds the data of authentication response.
     * This is used to extract the authentication response from the intent data passed to an activity
     * registered as the handler for User authentication.
     */
    fun authResponseFromIntent(intent: Intent): CustomMessage<*>? {
        return when {
            SDK_INT >= 33 ->
                intent.getParcelableExtra(
                    CUSTOM_MESSAGE_VALUE,
                    CustomMessage::class.java,
                )

            else -> intent.getParcelableExtra(CUSTOM_MESSAGE_VALUE) as? CustomMessage<*>
        }
    }

    /**
     * This function is used to check whether refresh token API failed after certain retry
     *
     * @return Boolean value which is comes true if the retry mechanism of refresh token failed to get the new token
     */
    fun isRefreshTokenFailed(): LiveData<Boolean> {
        return isRefreshTokenFailed
    }
}
