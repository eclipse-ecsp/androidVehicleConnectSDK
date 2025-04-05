package org.eclipse.ecsp.appauth

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
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ResponseTypeValues
import net.openid.appauth.TokenResponse
import org.eclipse.ecsp.environment.Environment
import org.eclipse.ecsp.environment.EnvironmentManager
import org.eclipse.ecsp.helper.Constant.CLIENT_SECRET
import org.eclipse.ecsp.helper.response.CustomMessage
import org.eclipse.ecsp.helper.response.error.CustomError
import org.eclipse.ecsp.helper.response.error.Status
import org.eclipse.ecsp.helper.sharedpref.AppDataStorage

/**
 * This class is used to handle the OAuth library operations to fetch the token
 *
 * @property context represents the context of SDK activity context
 * @property launcher this is the instance of ActivityResultLauncher of client application,
 * result will of oAuth token will be serve through launcher
 */
class AppAuthProvider(
    private val context: Context,
    private val launcher: ActivityResultLauncher<Intent>? = null,
) : AuthInterface {
    private var environmentData: Environment? = null
    private var authorizationService: AuthorizationService? = null

    companion object {
        private const val AUTHORIZATION_LOGIN_KEY = "login"
    }

    /**
     * This is used to initializing AutInterface and fetching EnvironmentData
     */
    init {
        AuthManager.sharedInterface(this)
        environmentData = EnvironmentManager.environment()
    }

    /**
     * This function is to set up the AuthorizationRequest which is needed for oAuth configuration
     *
     * @param authServiceConfig Holds OAuth library service configuration details
     * @param authParams holds necessary parameters required to get the token (like client secret id, grant, locale..etc )
     * @return this function returns AuthorizationRequest for oAuth
     */
    private fun getAuthorizationRequest(
        authServiceConfig: AuthorizationServiceConfiguration,
        authParams: HashMap<String, String>,
    ): AuthorizationRequest {
        environmentData.let {
            val clientId: String = environmentData!!.clientId!!
            val redirectUri = Uri.parse(environmentData!!.redirectUrl)
            val builder =
                AuthorizationRequest.Builder(
                    authServiceConfig,
                    clientId,
                    ResponseTypeValues.CODE,
                    redirectUri,
                )
            builder.setScopes(environmentData!!.scopes)
            builder.setAdditionalParameters(authParams)
            builder.setPrompt(AppAuthProvider.Companion.AUTHORIZATION_LOGIN_KEY)
            return builder.build()
        }
    }

    /**
     * This function is to launch the authorization Intent for sign in page of OAuth service
     *
     * @param result serves the sign in function error response
     */
    override suspend fun signIn(result: (CustomMessage<Any>) -> Unit) {
        if (environmentData?.clientId != null &&
            environmentData?.redirectUrl != null &&
            environmentData?.clientSecret != null
        ) {
            try {
                environmentData?.let {
                    val appAuthConfiguration =
                        OAuthServiceUtilities.getAppAuthConfiguration()
                    val authParams =
                        OAuthServiceUtilities.getParametersMap(it.clientSecret!!)
                    val signInServiceConfiguration =
                        OAuthServiceUtilities.getSignInServiceConfiguration()

                    authorizationService = AuthorizationService(context, appAuthConfiguration)
                    val intent =
                        authorizationService?.getAuthorizationRequestIntent(
                            getAuthorizationRequest(signInServiceConfiguration, authParams),
                        )
                    if (intent != null) {
                        launcher?.launch(intent)
                    }
                }
            } catch (e: Exception) {
                result(
                    CustomMessage(
                        Status.Failure,
                        CustomError.Generic(e.message),
                    ),
                )
            }
        } else {
            result(
                CustomMessage(
                    Status.Failure,
                    CustomError.EnvironmentNotConfigured,
                ),
            )
        }
    }

    /**
     * This function is to launch the authorization Intent for sign in page of OAuth service
     *
     * @param result serves the sign up function error response
     */
    override suspend fun signUp(result: (CustomMessage<Any>) -> Unit) {
        if (environmentData?.clientId != null &&
            environmentData?.redirectUrl != null &&
            environmentData?.clientSecret != null
        ) {
            try {
                environmentData?.let {
                    val appAuthConfiguration =
                        OAuthServiceUtilities.getAppAuthConfiguration()
                    val authParams =
                        OAuthServiceUtilities.getParametersMap(it.clientSecret!!)
                    val signUpServiceConfiguration =
                        OAuthServiceUtilities.getSignUpServiceConfiguration()

                    authorizationService =
                        AuthorizationService(
                            context,
                            appAuthConfiguration,
                        )
                    val intent =
                        authorizationService?.getAuthorizationRequestIntent(
                            getAuthorizationRequest(signUpServiceConfiguration, authParams),
                        )
                    if (intent != null) {
                        launcher?.launch(intent)
                    }
                }
            } catch (e: Exception) {
                result(
                    CustomMessage(
                        Status.Failure,
                        CustomError.Generic(e.message),
                    ),
                )
            }
        } else {
            result(
                CustomMessage(
                    Status.Failure,
                    CustomError.EnvironmentNotConfigured,
                ),
            )
        }
    }

    /**
     * This function is to serve the parsed token response or auth error response
     *
     * @param response is holding AuthorizationResponse of OAuth service on successful login
     * @param result is holding TokenResponse and AuthorizationException after parsing
     */
    override fun getAuthServices(
        response: AuthorizationResponse?,
        result: (
            tokenResponse: TokenResponse?,
            authorizationException: AuthorizationException?,
        ) -> Unit,
    ) {
        if (environmentData != null && environmentData!!.clientSecret != null) {
            val parametersMap = HashMap<String, String>()
            parametersMap[CLIENT_SECRET] = environmentData?.clientSecret!!
            response?.createTokenExchangeRequest(
                parametersMap,
            )?.let {
                authorizationService?.performTokenRequest(
                    it,
                ) { res, ex ->
                    result(res, ex)
                }
            }
        }
    }

    /**
     * This function is to dispose the authorizationService of oAuth service,
     * Function should call once the activity is destroyed.
     */
    override fun disposeService() {
        authorizationService?.dispose()
    }

    /**
     * Represents access token of the application
     */
    override var accessToken: String = AppDataStorage.getAppPrefInstance()?.accessToken ?: ""

    /**
     * Represents refresh token of the application
     */
    override var refreshToken: String = AppDataStorage.getAppPrefInstance()?.refreshToken ?: ""

    /**
     * Represents token type
     */
    override var tokenType: String = AppDataStorage.getAppPrefInstance()?.tokenType ?: ""

    /**
     * Represents token scope
     */
    override var scope: String = ""

    /**
     * Represents access token expiry date
     */
    override var accessTokenExpirationDate: Long = 0

    /**
     * Represents additional parameters which can be used
     */
    override var additionalParameters: HashMap<Any, Any> = HashMap()
}
