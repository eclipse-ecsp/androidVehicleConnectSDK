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
import okhttp3.ResponseBody
import org.eclipse.ecsp.helper.response.CustomMessage
import org.eclipse.ecsp.helper.response.error.CustomError
import org.eclipse.ecsp.helper.response.error.Status

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
 * This function is to create the CustomMessage error body from error response body of retrofit
 *
 * @param T Generic data class
 * @param responseBody Retrofit error response body served as API response
 * @return [CustomMessage] data class after converting error response body
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
 * Function is to generate a alpha numeric unique id
 * Converts timestamp to base-36 for compact alphanumeric representation and match the pattern.
 *
 * @param regexPattern used to create the string
 * @return Unique id as [String]
 */
fun getAlphaNumericId(regexPattern: String): String {
    val timestamp = System.currentTimeMillis().toString()
    val base36 = timestamp.toLong().toString(36) // Converts timestamp to base-36 for compact alphanumeric representation
    val id = base36.takeLast(10).padStart(10, '0') // Ensure length is 10

    // Replace any invalid characters if necessary, though base36 should be safe (contains a-z, 0-9)
    return id.replace(Regex(regexPattern), "")
}
