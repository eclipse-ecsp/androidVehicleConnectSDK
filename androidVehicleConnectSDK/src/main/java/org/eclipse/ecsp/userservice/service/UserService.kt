package org.eclipse.ecsp.userservice.service

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
import org.eclipse.ecsp.CustomEndPoint
import org.eclipse.ecsp.helper.AppManager
import org.eclipse.ecsp.helper.response.CustomMessage
import org.eclipse.ecsp.userservice.endpoint.UserEndPoint
import org.eclipse.ecsp.userservice.model.UserProfile
import org.eclipse.ecsp.userservice.repository.IUserRepository
import javax.inject.Inject

/**
 * UserService class is used by client application to call the User service functions
 *
 * @property activity Client application activity instance
 */
class UserService(private val activity: Activity) : UserServiceInterface {
    @Inject
    lateinit var iUserRepository: IUserRepository

    init {
        AppManager.getAppComponent().inject(this)
    }

    /**
     * This function is to do Sign In process by launching the UiApplication activity
     *
     * @param requestCode client application request code to send the result
     * @param launcher client application launcher instance to launch the UiApplication activity
     */
    override fun signInWithAppAuth(
        requestCode: Int,
        launcher: ActivityResultLauncher<Intent>,
    ) {
        iUserRepository.signInWithAppAuth(activity, requestCode, launcher)
    }

    /**
     * This function is to do sign up process by launching the UiApplication activity
     *
     * @param requestCode client application request code to send the result
     * @param launcher client application launcher instance to launch the UiApplication activity
     */
    override fun signUpWithAppAuth(
        requestCode: Int,
        launcher: ActivityResultLauncher<Intent>,
    ) {
        iUserRepository.signUpWithAppAuth(activity, requestCode, launcher)
    }

    /**
     * This function is to do sign out process by removing all shared preference data
     * related to the current user.
     *
     * @param result this is the callback higher order lambda function to pass the result to client application
     */
    override fun signOutWithAppAuth(result: (CustomMessage<Any>) -> Unit) {
        iUserRepository.signOutWithAppAuth(result)
    }

    /**
     * This function is to fetch the user profile data
     *
     * @param customMessage this is the callback higher order lambda function
     * to pass the result (UserProfileCollection) to client application
     */
    override suspend fun fetchUserProfile(customMessage: (CustomMessage<UserProfile>) -> Unit) {
        val userEndPoint = UserEndPoint.Profile
        val customEndPoint =
            org.eclipse.ecsp.CustomEndPoint(
                userEndPoint.baseUrl ?: "",
                userEndPoint.path,
                userEndPoint.method,
                userEndPoint.header,
                userEndPoint.body,
            )
        iUserRepository.fetchUserProfile(customEndPoint, customMessage)
    }
}

/**
 * UserServiceInterface is implemented by UserService to override the functions available in this interface
 * This interface has sign in, sign up , sign out and user profile fetching functions
 */
interface UserServiceInterface {
    /**
     * Represents to do SIGN IN
     *
     * @param requestCode holds activity result request code
     * @param launcher holds [ActivityResultLauncher] object
     */
    fun signInWithAppAuth(
        requestCode: Int,
        launcher: ActivityResultLauncher<Intent>,
    )

    /**
     * Represents to do SIGN UP
     *
     * @param requestCode holds activity result request code
     * @param launcher holds [ActivityResultLauncher] object
     */
    fun signUpWithAppAuth(
        requestCode: Int,
        launcher: ActivityResultLauncher<Intent>,
    )

    /**
     * Represents to do SIGN OUT
     *
     * @param result emits the [CustomMessage] using higher order function
     */
    fun signOutWithAppAuth(result: (CustomMessage<Any>) -> Unit)

    /**
     * Represents to fetch the user profile data
     *
     * @param customMessage higher order function to emit the [CustomMessage] value as response
     */
    suspend fun fetchUserProfile(customMessage: (CustomMessage<UserProfile>) -> Unit)

    companion object {
        /**
         * This function is used by client application to get the instance of UserService class
         *
         * @return UserService instance
         */
        @JvmStatic
        fun authService(activity: Activity): UserServiceInterface = UserService(activity)
    }
}
