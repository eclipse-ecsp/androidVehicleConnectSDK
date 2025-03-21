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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.eclipse.ecsp.helper.response.CustomMessage
import org.eclipse.ecsp.helper.response.error.CustomError
import org.eclipse.ecsp.helper.response.error.Status
import okhttp3.ResponseBody

/**
 * This function is used to convert the generic data class object to json string
 *
 * @param T Generic Data class Reference
 * @param data  Generic data class object
 * @return Json String value of converted data class
 */
internal inline fun <reified T> Gson.dataToJson(data: T): String = toJson(data)

/**
 * This function is used to convert json string to Respective data class
 *
 * @param T Generic data class
 * @param json json String as input
 * @return Respective data class object
 */
internal inline fun <reified T> Gson.fromJson(json: String): T = fromJson(json, object : TypeToken<T>() {}.type)

/**
 * This function is used to share the locale of device where the application is available
 *
 * @return String formatted locale value
 */
internal fun getLocale(): String = "en-US"

/**
 * This function is to create the CustomMessage error body from error response body of retrofit
 *
 * @param T Generic data class
 * @param responseBody Retrofit error response body served as API response
 * @return CustomMessage data class after converting error response body
 */
internal inline fun <reified T> networkError(
    responseBody: ResponseBody?,
    errorCode: Int?,
): CustomMessage<T> {
    return CustomMessage(
        Status.Failure,
        error =
            when (errorCode) {
                400 -> CustomError.InvalidRequest
                500 -> CustomError.ServerError
                401 -> CustomError.RefreshTokenFailed
                else -> {
                    CustomError.Generic(responseBody?.charStream()?.readText())
                }
            },
    )
}

/**
 * This function is to create the CustomMessage response part from retrofit response body
 *
 * @param T Generic data class
 * @param response Retrofit response body served as API response
 * @return CustomMessage data class after converting response body
 */
internal fun <T> networkResponse(response: T): CustomMessage<T> {
    val customMessage = CustomMessage<T>(Status.Success)
    customMessage.setResponseData(response)
    return customMessage
}
