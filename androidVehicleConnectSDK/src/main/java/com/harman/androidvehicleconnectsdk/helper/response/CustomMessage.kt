package com.harman.androidvehicleconnectsdk.helper.response

import android.os.Parcelable
import com.harman.androidvehicleconnectsdk.helper.response.error.CustomError
import com.harman.androidvehicleconnectsdk.helper.response.error.Status
import kotlinx.parcelize.Parcelize

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
/**
 * CustomMessage data class holds the value of response data, error data and status,
 * which will be serve to application
 *
 * @property status Boolean value from Status sealed class
 * @property error Error value from CustomError sealed class
 * @property response Response data of API
 */
@Parcelize
data class CustomMessage<T>(
    var status: Status,
    var error: CustomError? = null
) : Parcelable {
    @kotlinx.parcelize.IgnoredOnParcel
    var response: T? = null
    internal fun setResponseData(value: T) {
        response = value
    }
}