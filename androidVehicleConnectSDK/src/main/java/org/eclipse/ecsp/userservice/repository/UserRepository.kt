package org.eclipse.ecsp.userservice.repository

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
import androidx.activity.result.ActivityResultLauncher
import com.google.gson.Gson
import org.eclipse.ecsp.helper.AppManager
import org.eclipse.ecsp.helper.Constant.ACTION_KEY
import org.eclipse.ecsp.helper.Constant.REQUEST_CODE
import org.eclipse.ecsp.helper.Constant.SIGN_IN
import org.eclipse.ecsp.helper.Constant.SIGN_UP
import org.eclipse.ecsp.helper.fromJson
import org.eclipse.ecsp.helper.networkError
import org.eclipse.ecsp.helper.response.CustomMessage
import org.eclipse.ecsp.helper.response.error.Status
import org.eclipse.ecsp.helper.sharedpref.AppDataStorage
import org.eclipse.ecsp.network.EndPoint
import org.eclipse.ecsp.network.networkmanager.IRetrofitManager
import org.eclipse.ecsp.ui.UiApplication
import org.eclipse.ecsp.userservice.model.UserProfile
import javax.inject.Inject
import javax.inject.Singleton

/**
 * UserRepository class is used to API call related user profile and to do login activity
 * This class has login related functions and user profile function
 */
@Singleton
class UserRepository
    @Inject
    constructor() : IUserRepository {
        @Inject
        lateinit var retrofitManager: IRetrofitManager

        init {
            AppManager.getAppComponent().inject(this)
        }

        /**
         * This function is to do Sign In process by launching the UiApplication activity
         *
         * @param activity client application activity
         * @param requestCode client application request code to send the result
         * @param launcher client application launcher instance to launch the UiApplication activity
         */
        override fun signInWithAppAuth(
            context: Context,
            requestCode: Int,
            launcher: ActivityResultLauncher<Intent>,
        ) {
            launchActivity(context, SIGN_IN, requestCode, launcher)
        }

        /**
         * This function is to do sign up process by launching the UiApplication activity
         *
         * @param activity client application activity
         * @param requestCode client application request code to send the result
         * @param launcher client application launcher instance to launch the UiApplication activity
         */
        override fun signUpWithAppAuth(
            context: Context,
            requestCode: Int,
            launcher: ActivityResultLauncher<Intent>,
        ) {
            launchActivity(context, SIGN_UP, requestCode, launcher)
        }

        /**
         * This function is to do sign out process by removing all shared preference data
         * related to the current user.
         *
         * @param result this is the callback higher order lambda function to pass the result to client application
         */
        override fun signOutWithAppAuth(result: (CustomMessage<Any>) -> Unit) {
            AppDataStorage.getAppPrefInstance()?.removeAll()
            result(CustomMessage(Status.Success, null))
        }

        /**
         * This function is to fetch the user profile data via retrofit service
         *
         * @param endPoint this holds the required details like header, path, query, body...etc for API Call
         * @return [CustomMessage] of [UserProfile]
         */
        override suspend fun fetchUserProfile(endPoint: EndPoint): CustomMessage<UserProfile> {
            retrofitManager.sendRequest(endPoint).also {
                return if (it != null && it.isSuccessful) {
                    val data = Gson().fromJson<UserProfile>(it.body().toString())
                    val resp = CustomMessage<UserProfile>(Status.Success)
                    resp.setResponseData(data)
                    resp
                } else {
                    networkError(it?.errorBody(), it?.code())
                }
            }
        }

        /**
         *  Function is to trigger the password change API request
         *
         * @param endPoint this holds the required details like header, path, query, body...etc for API request
         * @return [CustomMessage] contain the success or failure details
         */
        override suspend fun changePasswordRequest(endPoint: EndPoint): CustomMessage<Any> {
            retrofitManager.sendRequestWithNoResponse(endPoint).also {
                return if (it != null && it.isSuccessful) {
                    CustomMessage(Status.Success)
                } else {
                    networkError(it?.errorBody(), it?.code())
                }
            }
        }

        /**
         * This function is to launch the UIApplication activity using client activity instance
         *
         * @param activity client activity instance
         * @param action is the static string to determine which process to start
         * @param requestCode client application request code to send the result
         * @param launcher client application launcher instance to launch the UiApplication activity
         */
        private fun launchActivity(
            context: Context,
            action: String,
            requestCode: Int,
            launcher: ActivityResultLauncher<Intent>,
        ) {
            val intent = Intent(context, UiApplication::class.java)
            intent.putExtra(ACTION_KEY, action)
            intent.putExtra(REQUEST_CODE, requestCode)
            launcher.launch(intent)
        }
    }

/**
 * IUserRepository interface is implemented by UserRepository to override the functions available in this interface
 * This interface contains sign in, sign up , sign out and user profile fetching functions
 */
interface IUserRepository {
    /**
     * Represents to do SIGN IN using SDK UI
     *
     * @param context holds application Context
     * @param requestCode holds activity result request code
     * @param launcher holds [ActivityResultLauncher] object
     */
    fun signInWithAppAuth(
        context: Context,
        requestCode: Int,
        launcher: ActivityResultLauncher<Intent>,
    )

    /**
     * represents to do SIGN UP using SDK UI
     *
     * @param context holds application Context
     * @param requestCode holds activity result request code
     * @param launcher holds [ActivityResultLauncher] object
     */
    fun signUpWithAppAuth(
        context: Context,
        requestCode: Int,
        launcher: ActivityResultLauncher<Intent>,
    )

    /**
     * represents to do SIGN OUT using SDK UI
     *
     * @param result emits the [CustomMessage] using higher order function
     */
    fun signOutWithAppAuth(result: (CustomMessage<Any>) -> Unit)

    /**
     * Represents to fetch the user profile data
     *
     * @param endPoint End points of API
     * @return [CustomMessage] of [UserProfile] value as response
     */
    suspend fun fetchUserProfile(endPoint: EndPoint): CustomMessage<UserProfile>

    /**
     * Represents the interface method to trigger the password change API request
     *
     * @param endPoint End points of API
     * @return [CustomMessage] contain the success or failure details
     */
    suspend fun changePasswordRequest(endPoint: EndPoint): CustomMessage<Any>
}
