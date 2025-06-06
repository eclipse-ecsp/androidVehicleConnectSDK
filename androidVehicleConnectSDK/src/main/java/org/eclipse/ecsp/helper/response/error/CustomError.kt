package org.eclipse.ecsp.helper.response.error

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
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.eclipse.ecsp.helper.Constant.ENVIRONMENT_NOT_CONFIGURED
import org.eclipse.ecsp.helper.Constant.INVALID_REQUEST
import org.eclipse.ecsp.helper.Constant.REFRESH_TOKEN_FAILED_ERROR
import org.eclipse.ecsp.helper.Constant.SERVER_ERROR
import org.eclipse.ecsp.helper.Constant.UNAUTHORIZED_ERROR
import org.eclipse.ecsp.helper.Constant.WRONG_INTENT

/**
 * CustomError Sealed class used for customizing the message based on the scenario
 * Contains sub sealed classes for Network Errors, response Status, and generic errors messages
 *
 * @property message Serve the respective messages
 */
@Parcelize
sealed class CustomError(var message: String) : Parcelable {
    /**
     * This sealed class for serving network related errors
     *
     * @property message Servers Network error messages
     */
    @Parcelize
    sealed class NetworkError(val message: String) : Parcelable {
        data object UnAuthorized : NetworkError(UNAUTHORIZED_ERROR)

        @Parcelize
        data class Unknown(var unknownMessage: String) : NetworkError(unknownMessage), Parcelable
    }

    /**
     * Represents error message says Invalid Intent
     */
    data object InvalidIntent : CustomError(WRONG_INTENT)

    /**
     * Represents error message says Invalid Request
     */
    data object InvalidRequest : CustomError(INVALID_REQUEST)

    /**
     * Represents error message says failed refresh token
     */
    data object RefreshTokenFailed : CustomError(REFRESH_TOKEN_FAILED_ERROR)

    /**
     * Represents error message says environment is not configured correctly
     */
    data object EnvironmentNotConfigured : CustomError(ENVIRONMENT_NOT_CONFIGURED)

    /**
     * Represents error message says server error occurred
     */
    data object ServerError : CustomError(SERVER_ERROR)

    @Parcelize
    data class Generic(val genericMessage: String? = null) : CustomError(genericMessage ?: "")
}

/**
 * Status sealed class hold response status boolean value
 *
 * @property requestStatus serve the boolean value respective to the response
 */
@Parcelize
sealed class Status(val requestStatus: Boolean) : Parcelable {
    data object Success : Status(true)

    data object Failure : Status(false)
}
