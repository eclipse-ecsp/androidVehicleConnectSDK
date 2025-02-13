package com.harman.androidvehicleconnectsdk.vehicleservice.model.deviceassociation

import com.google.gson.annotations.SerializedName

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
 * AssociatedDeviceInfo data class holds the associated device API response
 *
 * @property mCode device code
 * @property mMessage message value
 * @property mAssociationInfo association information holds by AssociationInfo inner class
 */
data class AssociatedDeviceInfo(
    @SerializedName("code") val mCode: String?,
    @SerializedName("message") val mMessage: String?,
    @SerializedName("data") val mAssociationInfo: AssociationInfo?
) {
    data class AssociationInfo(
        @SerializedName("associationID") val mAssociationID: Long,
        @SerializedName("associationStatus") val mAssociationStatus: String?
    )
}
