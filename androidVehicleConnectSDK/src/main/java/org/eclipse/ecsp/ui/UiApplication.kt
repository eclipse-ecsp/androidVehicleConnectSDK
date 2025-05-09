package org.eclipse.ecsp.ui

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

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.TokenResponse
import org.eclipse.ecsp.appauth.AuthInterface
import org.eclipse.ecsp.appauth.AuthManager
import org.eclipse.ecsp.helper.Constant.ACTION_KEY
import org.eclipse.ecsp.helper.Constant.CUSTOM_MESSAGE_VALUE
import org.eclipse.ecsp.helper.Constant.REQUEST_CODE
import org.eclipse.ecsp.helper.Constant.SIGN_IN
import org.eclipse.ecsp.helper.Constant.SIGN_OUT
import org.eclipse.ecsp.helper.Constant.SIGN_UP
import org.eclipse.ecsp.helper.response.CustomMessage
import org.eclipse.ecsp.helper.response.error.CustomError
import org.eclipse.ecsp.helper.response.error.Status
import org.eclipse.ecsp.network.debugprint.DebugPrint

/**
 * UiApplication activity class is used to launch the custom browser tab to open the login and sign up page using OAuth lib
 * This activity is opened using the client applications context
 */
class UiApplication : ComponentActivity() {
    private var activityLauncher: ActivityResultLauncher<Intent>? = null
    private var authInterface: AuthInterface? = null

    /**
     * This function is a android activity function which is override and which handles activity result values
     *
     * @param savedInstanceState is to save the state instance of activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        DebugPrint.d("AppAuth", "Entered RESULT OK")
                        val data = result.data
                        // success
                        data?.let {
                            AuthorizationResponse.fromIntent(it)?.let { authRes ->
                                authInterface?.getAuthServices(authRes) { tokenResponse, authException ->
                                    if (tokenResponse != null) {
                                        setCallBackValue(CustomMessage(Status.Success), tokenResponse)
                                        DebugPrint.d("AppAuth", "Entered RESULT OK -> success")
                                    }
                                    if (authException != null) {
                                        setCallBackValue(
                                            CustomMessage(
                                                Status.Failure,
                                                CustomError.Generic(
                                                    authException.error
                                                        ?: CustomError.NetworkError.UnAuthorized.message,
                                                ),
                                            ),
                                        )
                                    }
                                }
                            }
                        }
                        // exception
                        data?.let {
                            AuthorizationException.fromIntent(it).let { exception ->
                                if (exception != null) {
                                    setCallBackValue(
                                        CustomMessage(
                                            Status.Failure,
                                            CustomError.Generic(
                                                exception.error
                                                    ?: CustomError.NetworkError.UnAuthorized.message,
                                            ),
                                        ),
                                    )
                                    DebugPrint.d("AppAuth", "Entered RESULT OK -> exception")
                                }
                            }
                        }
                    }
                    RESULT_CANCELED -> {
                        DebugPrint.d("AppAuth", "Entered RESULT CANCELED -> exception")
                        setErrorCallBack()
                    }
                    else -> {
                        DebugPrint.d("AppAuth", "NOT Entered RESULT :OK or CANCELED")
                        setErrorCallBack()
                    }
                }
            }
        authInterface = AuthInterface.appAuthInterface(this, activityLauncher!!)
        launchCoroutineAction()
    }

    /**
     * Function is to set error message in callback
     *
     */
    private fun setErrorCallBack() {
        setCallBackValue(
            CustomMessage(
                Status.Failure,
                CustomError.Generic(CustomError.NetworkError.UnAuthorized.message),
            ),
        )
    }

    /**
     * Represents to trigger the function available under AuthInterface like SIGN IN, SIGN OUT and SIGN OUT
     *
     */
    private fun launchCoroutineAction() {
        val exception =
            CoroutineExceptionHandler { _, exception ->
                DebugPrint.e("AppAuth: ", exception.cause.toString())
                setCallBackValue(
                    CustomMessage(
                        Status.Failure,
                        CustomError.Generic(exception.toString()),
                    ),
                )
            }
        CoroutineScope(IO).launch(exception) {
            if (intent != null && intent.hasExtra(ACTION_KEY)) {
                when (intent.getStringExtra(ACTION_KEY)) {
                    SIGN_IN -> {
                        authInterface?.signIn { msg ->
                            msg.status.requestStatus.let { setCallBackValue(msg) }
                        }
                    }

                    SIGN_UP -> {
                        authInterface?.signUp { msg ->
                            msg.status.requestStatus.let { setCallBackValue(msg) }
                        }
                    }

                    SIGN_OUT -> {
                        authInterface?.signOut { msg ->
                            msg.status.requestStatus.let { setCallBackValue(msg) }
                        }
                    }

                    else -> {
                        setCallBackValue(
                            CustomMessage(
                                Status.Failure,
                                CustomError.InvalidIntent,
                            ),
                        )
                    }
                }
            } else {
                setCallBackValue(
                    CustomMessage(
                        Status.Failure,
                        CustomError.InvalidIntent,
                    ),
                )
                return@launch
            }
        }
    }

    /**
     * This function is to set the callback value to client application
     * CustomMessage value is put inside the intent parcelable container and passed to client app ActivityResult
     * Once the Result is set activity will be destroyed
     *
     * @param value is CustomMessage data class which contains the status
     * @param tokenResponse is the authentication response value contains the token details
     */
    private fun setCallBackValue(
        value: CustomMessage<Any>,
        tokenResponse: TokenResponse? = null,
    ) {
        runBlocking {
            if (tokenResponse != null) {
                setAuthTokenState(tokenResponse)
            }
        }
        val intent =
            Intent().apply {
                putExtra(CUSTOM_MESSAGE_VALUE, value)
            }
        setResult(getIntent().getIntExtra(REQUEST_CODE, 0), intent)
        finish()
    }

    /**
     * This function is called once the activity is killed, and Authorization service will be disposed
     *
     */
    override fun onDestroy() {
        super.onDestroy()
        authInterface?.disposeService()
    }

    /**
     * This function is to set the Auth token details to AuthInterface and shared preference
     *
     * @param tokenResponse Token details from OAuth service
     */
    private fun setAuthTokenState(tokenResponse: TokenResponse) {
        AuthManager.authInterface?.accessToken =
            tokenResponse.accessToken ?: ""
        AuthManager.authInterface?.refreshToken =
            tokenResponse.refreshToken ?: ""
        AuthManager.authInterface?.tokenType =
            tokenResponse.tokenType ?: ""
        AuthManager.authInterface?.scope =
            tokenResponse.scope ?: ""
        AuthManager.authInterface?.accessTokenExpirationDate =
            tokenResponse.accessTokenExpirationTime ?: 0L
        AuthManager().saveTokenState()
    }
}
