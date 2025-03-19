package com.harman.androidvehicleconnectsdk.notificationservice.model
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
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Represents the AlertAnalysisData model class
 *
 * @property pagination hold the AlertPaginationData details
 * @property alertList hold the AlertData list details
 */
data class AlertAnalysisData(
    @SerializedName("pagination") var pagination: AlertPaginationData,
    @SerializedName("alerts") var alertList: List<AlertData>?,
) {
    /**
     * Represents the AlertPaginationData model class
     *
     * @property page holds the page integer value
     * @property size holds the size integer value
     * @property total holds the AlertPaginationTotal value
     */
    data class AlertPaginationData(
        @SerializedName("page") var page: Int,
        @SerializedName("size") var size: Int,
        @SerializedName("total") var total: AlertPaginationTotal,
    )

    /**
     * Represents the AlertPaginationTotal model class
     *
     * @property records records count in integer
     * @property pages page value in integer
     */
    data class AlertPaginationTotal(
        @SerializedName("records") var records: Int,
        @SerializedName("pages") var pages: Int,
    )
}

/**
 * Represents the AlertData model class
 *
 * @property id ID of Alert data
 * @property timestamp timestamp of alert Data
 * @property pdId pdid of alert Data
 * @property userId userid of alert Data
 * @property alertType alert type of alert Data
 * @property payload payload of alert Data
 * @property alertMessage alertMessage of alert Data
 * @property read read value of alert Data
 * @property deleted deleted value of alert Data
 */
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
    @SerializedName("deleted") var deleted: Boolean,
) : Parcelable

/**
 * Represents the PayLoadData model class
 *
 * @property eventID eventID of PayLoadData
 * @property data data of PayLoadData
 * @property version version of PayLoadData
 * @property benchMode benchMode of PayLoadData
 * @property timezone timezone of PayLoadData
 * @property timestamp timestamp of PayLoadData
 * @property pdId pdId of PayLoadData
 */
@Parcelize
data class PayLoadData(
    @SerializedName("EventID") var eventID: String?,
    @SerializedName("Data") var data: DataItem?,
    @SerializedName("Version") var version: String?,
    @SerializedName("BenchMode") var benchMode: Int?,
    @SerializedName("Timezone") var timezone: Int?,
    @SerializedName("Timestamp") var timestamp: Long?,
    @SerializedName("pdid") var pdId: String?,
) : Parcelable

/**
 * Represents the DataItem model class which is available in PayLoadData
 *
 * @property alertDataProperties holds AlertDataPropertiesData values
 * @property latitude holds latitude values
 * @property longitude holds longitude values
 * @property tripId holds tripId values
 * @property brand holds brand values
 * @property status holds status values
 */
@Parcelize
data class DataItem(
    @SerializedName("alertDataProperties") var alertDataProperties: AlertDataPropertiesData?,
    @SerializedName("latitude") var latitude: Double?,
    @SerializedName("longitude") var longitude: Double?,
    @SerializedName("tripid") var tripId: String?,
    @SerializedName("brand") var brand: String?,
    @SerializedName("status") var status: String?,
) : Parcelable

/**
 * Represents the AlertDataPropertiesData model class value
 *
 * @property latitude holds latitude value
 * @property longitude holds longitude value
 * @property tripId holds tripId value
 * @property brand holds brand value
 * @property status holds status value
 */
@Parcelize
data class AlertDataPropertiesData(
    @SerializedName("latitude") var latitude: Double?,
    @SerializedName("longitude") var longitude: Double?,
    @SerializedName("tripid") var tripId: String?,
    @SerializedName("brand") var brand: String?,
    @SerializedName("status") var status: String?,
) : Parcelable
