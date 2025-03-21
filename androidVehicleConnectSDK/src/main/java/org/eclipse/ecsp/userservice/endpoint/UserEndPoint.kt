package org.eclipse.ecsp.userservice.endpoint

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
import org.eclipse.ecsp.environment.EnvironmentManager
import org.eclipse.ecsp.helper.Constant
import org.eclipse.ecsp.helper.Constant.GRANT_TYPE_KEY
import org.eclipse.ecsp.helper.Constant.GRANT_TYPE_VALUE
import org.eclipse.ecsp.helper.Constant.HEADER_AUTHORIZATION
import org.eclipse.ecsp.helper.Constant.HEADER_CONTENT_TYPE_KEY
import org.eclipse.ecsp.helper.Constant.HEADER_CONTENT_TYPE_VALUE
import org.eclipse.ecsp.helper.getLocale
import org.eclipse.ecsp.network.EndPoint
import org.eclipse.ecsp.network.RequestMethod

/**
 * UserEndPoint sealed class is used to configure the User related API endpoints and other details
 * This sealed class is implemented EndPoint Interface
 *
 * @property name Is used to provide the name of object of sealed class
 */
sealed class UserEndPoint(val name: String) : EndPoint {
    companion object {
        private const val SIGN_IN = "SignIn"
        private const val SIGN_UP = "SignUp"
        private const val AUTH_TOKEN = "AuthToken"
        private const val REFRESH_TOKEN = "RefreshToken"
        private const val PROFILE = "Profile"
    }

    data object SignIn : UserEndPoint(SIGN_IN)

    data object SignUp : UserEndPoint(SIGN_UP)

    data object AuthToken : UserEndPoint(AUTH_TOKEN)

    data object RefreshToken : UserEndPoint(REFRESH_TOKEN)

    data object Profile : UserEndPoint(PROFILE)

    /**
     * Endpoint interface implementation method to set the path (end point of base url)
     */
    override var path: String? =
        when (this.name) {
            SIGN_IN -> "/oauth2/authorize"
            SIGN_UP -> "/sign-up/"
            AUTH_TOKEN -> "/oauth2/token"
            REFRESH_TOKEN -> {
                val grantType = "${GRANT_TYPE_KEY}=${GRANT_TYPE_VALUE}"
                "/oauth2/token?$grantType&${GRANT_TYPE_VALUE}="
            }

            PROFILE -> "/v1/users/self"
            else -> ""
        }

    /**
     * Endpoint interface implementation method to set the base url for User related calls
     */
    override var baseUrl: String? =
        when (this.name) {
            SIGN_IN -> EnvironmentManager.environment()?.signinUrl.toString()
            REFRESH_TOKEN -> EnvironmentManager.environment()?.signinUrl.toString()
            SIGN_UP -> EnvironmentManager.environment()?.signupUrl.toString()
            PROFILE -> EnvironmentManager.environment()?.profileUrl.toString()
            else -> ""
        }

    /**
     * Endpoint interface implementation method to set the Request method
     */
    override var method: RequestMethod? =
        when (this.name) {
            REFRESH_TOKEN -> RequestMethod.Post
            else -> {
                RequestMethod.Get
            }
        }

    /**
     * Endpoint interface implementation method to set the headers
     */
    override var header: HashMap<String, String>? =
        when (this.name) {
            REFRESH_TOKEN -> {
                HashMap<String, String>().apply {
                    val headerValue =
                        "${EnvironmentManager.environment()?.clientId}:${EnvironmentManager.environment()?.clientSecret}"
                    val encodedHeaderValue =
                        android.util.Base64.encodeToString(
                            headerValue.toByteArray(),
                            android.util.Base64.DEFAULT,
                        ).trim()
                    put(HEADER_AUTHORIZATION, "Basic $encodedHeaderValue")
                    put(HEADER_CONTENT_TYPE_KEY, HEADER_CONTENT_TYPE_VALUE)
                }
            }

            PROFILE -> {
                HashMap<String, String>().apply {
                    put(Constant.HEADER_ACCEPT, Constant.HEADER_APPLICATION_JSON)
                    put(Constant.HEADER_ACCEPT_LANGUAGE, getLocale())
                }
            }

            else -> HashMap()
        }

    /**
     * Endpoint interface implementation method to set the body
     */
    override var body: Any? = Any()
}
