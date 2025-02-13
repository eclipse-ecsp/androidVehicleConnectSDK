package com.harman.androidvehicleconnectsdk.notificationservice.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
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
data class AlertAnalysisData(
    @SerializedName("pagination") var pagination: AlertPaginationData,
    @SerializedName("alerts") var alertList: List<AlertData>?
) {

    data class AlertPaginationData(
        @SerializedName("page") var page: Int,
        @SerializedName("size") var size: Int,
        @SerializedName("total") var total: AlertPaginationTotal
    )

    data class AlertPaginationTotal(
        @SerializedName("records") var records: Int,
        @SerializedName("pages") var pages: Int
    )
}

@Parcelize
data class AlertData(
    @SerializedName("id") var id: String?,
    @SerializedName("timestamp") var timestamp: Long?,
    @SerializedName("pdid") var pdId: String?,
    @SerializedName("userId") var userId: String?,
    @SerializedName("alertType") var alertType: String?,
    @SerializedName("payload") var payload: PayLoadData?,
    @SerializedName("alertMessage") var alertMessage: String?,
    @SerializedName("read") var read: Boolean,
    @SerializedName("deleted") var deleted: Boolean
) :Parcelable
@Parcelize
data class PayLoadData(
    @SerializedName("EventID") var eventID: String?,
    @SerializedName("Data") var data: DataItem?,
    @SerializedName("Version") var version: String?,
    @SerializedName("BenchMode") var benchMode: Int?,
    @SerializedName("Timezone") var timezone: Int?,
    @SerializedName("Timestamp") var timestamp: Long?,
    @SerializedName("pdid") var pdId: String?
) :Parcelable
@Parcelize
data class DataItem(
    @SerializedName("alertDataProperties") var alertDataProperties: AlertDataPropertiesData?,
    @SerializedName("latitude") var latitude: Double?,
    @SerializedName("longitude") var longitude: Double?,
    @SerializedName("tripid") var tripId: String?,
    @SerializedName("brand") var brand: String?,
    @SerializedName("status") var status: String?
) :Parcelable
@Parcelize
data class AlertDataPropertiesData(
    @SerializedName("latitude") var latitude: Double?,
    @SerializedName("longitude") var longitude: Double?,
    @SerializedName("tripid") var tripId: String?,
    @SerializedName("brand") var brand: String?,
    @SerializedName("status") var status: String?
) :Parcelable


