package com.harman.androidvehicleconnectsdk.roservice.model

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
 * RoEventHistoryResponse data class holds the response value come from RO History API
 *
 * @property roEvents Ro event data are holding
 * @property roStatus RO status value
 */
data class RoEventHistoryResponse(
    @SerializedName("roEvent") var roEvents: RoEventData,
    @SerializedName("roStatus") var roStatus: String
)

data class RoEventData(
    @SerializedName("EventID") var eventID: String?,
    @SerializedName("Version") var version: String?,
    @SerializedName("Timestamp") var timestamp: Long?,
    @SerializedName("RequestId") var requestId: String?,
    @SerializedName("Data") var data: RoData?
) {

    data class RoData(
        @SerializedName("state") var mState: String?,
        @SerializedName("roRequestId") var roRequestId: String?,
        @SerializedName("duration") var mDuration: Int?
    )
}
