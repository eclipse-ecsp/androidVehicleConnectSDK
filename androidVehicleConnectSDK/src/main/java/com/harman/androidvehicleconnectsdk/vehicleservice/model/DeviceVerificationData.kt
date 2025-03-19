package com.harman.androidvehicleconnectsdk.vehicleservice.model
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
import com.google.gson.annotations.SerializedName

/**
 * DeviceVerificationData data class is holding the Device verification status
 *
 * @property mDeviceVerificationMessages message value
 * @property mVerificationStatus device verification status
 */
data class DeviceVerificationData(
    @SerializedName("messages") val mDeviceVerificationMessages: DeviceVerificationMessages?,
    @SerializedName("results") val mVerificationStatus: VerificationStatus?,
) {
    data class DeviceVerificationMessages(
        @SerializedName("key") val key: String,
    )

    data class VerificationStatus(
        @SerializedName("isVerified") val isVerified: Boolean,
    )
}
