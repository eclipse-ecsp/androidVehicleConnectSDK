package com.harman.androidvehicleconnectsdk.vehicleservice.model

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
/**
 * DeviceAssociationListData data class hold the data of associated device list
 *
 * @property code code value
 * @property message message value
 * @property data List of associated device info
 */
@Parcelize
data class DeviceAssociationListData(
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<AssociatedDevice>
): Parcelable
@Parcelize
data class MetaData(
    @SerializedName("userVehicleAuthStatus")
    val userVehicleAuthStatus: String? = null
): Parcelable

/**
 * Represents to hold the Associated device detail
 *
 * @property mAssociationId holds device associated id
 * @property mSerialNumber holds device serial number
 * @property mAssociationStatus holds device association status
 * @property mDeviceId holds device id
 * @property mAssociatedOn holds device associated date
 * @property mDisassociatedOn holds device disassociated date
 * @property mImei holds device IMEI
 * @property mSsid holds device SSID
 * @property mIccid holds device ICCID
 * @property mMsisdn holds device MSISDN
 * @property mImsi holds device IMSI
 * @property mBssid holds device BSSID
 * @property mSoftwareVersion holds device software version value
 * @property mDeviceType holds device type
 * @property mSimStatus holds device sim status
 * @property mVin holds device vin details
 * @property metaData holds device meta data
 */
@Parcelize
data class AssociatedDevice(
    @SerializedName("associationId")
    val mAssociationId: Long = 0,
    @SerializedName("serialNumber")
    val mSerialNumber: String? = null,
    @SerializedName("associationStatus")
    val mAssociationStatus: String? = null,
    @SerializedName("deviceId")
    val mDeviceId: String? = null,
    @SerializedName("associatedOn")
    val mAssociatedOn: String? = null,
    @SerializedName("disassociatedOn")
    val mDisassociatedOn: String? = null,
    @SerializedName("imei")
    val mImei: String? = null,
    @SerializedName("ssid")
    val mSsid: String? = null,
    @SerializedName("iccid")
    val mIccid: String? = null,
    @SerializedName("msisdn")
    val mMsisdn: String? = null,
    @SerializedName("imsi")
    val mImsi: String? = null,
    @SerializedName("bssid")
    val mBssid: String? = null,
    @SerializedName("softwareVersion")
    val mSoftwareVersion: String? = null,
    @SerializedName("deviceType")
    val mDeviceType: String? = null,
    @SerializedName("simTranStatus")
    val mSimStatus: String? = null,
    @SerializedName("vin")
    val mVin: String? = null,
    @SerializedName("metadata")
    var metaData: MetaData? = null
) : Parcelable