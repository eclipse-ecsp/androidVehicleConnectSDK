package com.harman.androidvehicleconnectsdk.userservice.repository

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
import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.google.gson.Gson
import com.harman.androidvehicleconnectsdk.CustomEndPoint
import com.harman.androidvehicleconnectsdk.helper.AppManager
import com.harman.androidvehicleconnectsdk.helper.Constant.ACTION_KEY
import com.harman.androidvehicleconnectsdk.helper.Constant.REQUEST_CODE
import com.harman.androidvehicleconnectsdk.helper.Constant.SIGN_IN
import com.harman.androidvehicleconnectsdk.helper.Constant.SIGN_UP
import com.harman.androidvehicleconnectsdk.helper.fromJson
import com.harman.androidvehicleconnectsdk.helper.networkError
import com.harman.androidvehicleconnectsdk.helper.networkResponse
import com.harman.androidvehicleconnectsdk.helper.response.CustomMessage
import com.harman.androidvehicleconnectsdk.helper.response.error.Status
import com.harman.androidvehicleconnectsdk.helper.sharedpref.AppDataStorage
import com.harman.androidvehicleconnectsdk.network.networkmanager.IRetrofitManager
import com.harman.androidvehicleconnectsdk.ui.UiApplication
import com.harman.androidvehicleconnectsdk.userservice.model.UserProfile
import javax.inject.Inject
import javax.inject.Singleton

/**
 * UserRepository class is used to API call related user profile and to do login activity
 * This class has login related functions and user profile function
 */
@Singleton
class UserRepository @Inject constructor() : IUserRepository {
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
        activity: Activity,
        requestCode: Int,
        launcher: ActivityResultLauncher<Intent>
    ) {
        launchActivity(activity, SIGN_IN, requestCode, launcher)
    }

    /**
     * This function is to do sign up process by launching the UiApplication activity
     *
     * @param activity client application activity
     * @param requestCode client application request code to send the result
     * @param launcher client application launcher instance to launch the UiApplication activity
     */
    override fun signUpWithAppAuth(
        activity: Activity,
        requestCode: Int,
        launcher: ActivityResultLauncher<Intent>
    ) {
        launchActivity(activity, SIGN_UP, requestCode, launcher)
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
     * @param customEndPoint this holds the required details like header, path, query, body...etc for API Call
     * @param customMessage this is the callback higher order lambda function
     * to pass the result (UserProfileCollection) to client application
     */
    override suspend fun fetchUserProfile(customEndPoint: CustomEndPoint, customMessage: (CustomMessage<UserProfile>) -> Unit) {

        retrofitManager.sendRequest(customEndPoint).also {
            if (it != null && it.isSuccessful) {
                val data = Gson().fromJson<UserProfile>(it.body().toString())
                customMessage(networkResponse(data))
            } else {
                customMessage(networkError(it?.errorBody(), it?.code()))
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
        activity: Activity,
        action: String, requestCode: Int, launcher: ActivityResultLauncher<Intent>
    ) {
        val intent = Intent(activity, UiApplication::class.java)
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
     * @param activity holds application activity
     * @param requestCode holds activity result request code
     * @param launcher holds [ActivityResultLauncher] object
     */
    fun signInWithAppAuth(
        activity: Activity,
        requestCode: Int,
        launcher: ActivityResultLauncher<Intent>
    )

    /**
     * represents to do SIGN UP using SDK UI
     *
     * @param activity holds application activity
     * @param requestCode holds activity result request code
     * @param launcher holds [ActivityResultLauncher] object
     */
    fun signUpWithAppAuth(
        activity: Activity,
        requestCode: Int,
        launcher: ActivityResultLauncher<Intent>
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
     * @param customEndPoint End points of API
     * @param customMessage higher order function to emit the [CustomMessage] value as response
     */
    suspend fun fetchUserProfile(customEndPoint: CustomEndPoint, customMessage: (CustomMessage<UserProfile>) -> Unit)
}