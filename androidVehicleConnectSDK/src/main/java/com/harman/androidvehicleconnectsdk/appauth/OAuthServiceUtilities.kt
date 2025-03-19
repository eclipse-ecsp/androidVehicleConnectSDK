package com.harman.androidvehicleconnectsdk.appauth
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
import android.net.Uri
import com.harman.androidvehicleconnectsdk.environment.EnvironmentManager
import com.harman.androidvehicleconnectsdk.helper.Constant.CLIENT_SECRET
import com.harman.androidvehicleconnectsdk.userservice.endpoint.UserEndPoint
import net.openid.appauth.AppAuthConfiguration
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.browser.BrowserAllowList
import net.openid.appauth.browser.VersionedBrowserMatcher
import java.util.Locale

/**
 * OAuthServiceUtilities is a kotlin singleton class which has method to configure the OAuth service
 */
object OAuthServiceUtilities {
    private const val AUTHORIZATION_GRANT = "grant"
    private const val LOCALE = "locale"
    private const val AUTHORIZATION_CODE = "authorization_code"

    /**
     * This function is used to whitelist the browsers which application launch the Authentication page
     *
     * @return AppAuthConfiguration - OAuth service class object
     */
    fun getAppAuthConfiguration(): AppAuthConfiguration {
        return AppAuthConfiguration.Builder()
            .setBrowserMatcher(
                BrowserAllowList(
                    VersionedBrowserMatcher.CHROME_BROWSER,
                    VersionedBrowserMatcher.FIREFOX_BROWSER,
                    VersionedBrowserMatcher.SAMSUNG_BROWSER,
                    VersionedBrowserMatcher.CHROME_CUSTOM_TAB,
                    VersionedBrowserMatcher.SAMSUNG_CUSTOM_TAB,
                ),
            )
            .build()
    }

    /**
     * This function creates Hashmap which holds the config details for OAuth service
     *
     * @param clientSecretId - Environment specific secret id, received from client application
     * @return HashMap which holds the OAuth service config data
     */
    fun getParametersMap(clientSecretId: String): HashMap<String, String> {
        val parametersMap = HashMap<String, String>()
        parametersMap[AUTHORIZATION_GRANT] = AUTHORIZATION_CODE
        parametersMap[CLIENT_SECRET] = clientSecretId
        parametersMap[LOCALE] = Locale.getDefault().language
        return parametersMap
    }

    /**
     * This function creates the AuthorizationServiceConfiguration for Sign in page using Sign In URL and token URL
     *
     * @return AuthorizationServiceConfiguration is required to launch the Sign in page
     */
    fun getSignInServiceConfiguration(): AuthorizationServiceConfiguration {
        val baseUrl = EnvironmentManager.environment()?.signinUrl.toString()
        val accessUrl = "${baseUrl}${UserEndPoint.SignIn.path}"
        val tokenUrl = "${baseUrl}${UserEndPoint.AuthToken.path}"
        return AuthorizationServiceConfiguration(Uri.parse(accessUrl), Uri.parse(tokenUrl))
    }

    /**
     * This function creates the AuthorizationServiceConfiguration for Sign up page using Sign up URL and token URL
     *
     * @return AuthorizationServiceConfiguration is required to launch the Sign up page
     */
    fun getSignUpServiceConfiguration(): AuthorizationServiceConfiguration {
        val baseUrl = EnvironmentManager.environment()?.signupUrl.toString()
        val accessUrl = "${baseUrl}${UserEndPoint.SignUp.path}"
        val tokenUrl = "${baseUrl}${UserEndPoint.AuthToken.path}"
        return AuthorizationServiceConfiguration(Uri.parse(accessUrl), Uri.parse(tokenUrl))
    }
}
