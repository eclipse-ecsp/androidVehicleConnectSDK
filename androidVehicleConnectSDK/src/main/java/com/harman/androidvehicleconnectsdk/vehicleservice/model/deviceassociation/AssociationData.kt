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
 * AssociationData data class is used to hold the association information of device to pass to API
 *
 * @property mImei IMEI of device
 * @property mSerialNumber serial no of device
 * @property mSsid SSID of device
 * @property mIcCid ICCID of device
 * @property mMsisdn MSISDN of device
 * @property mImSi IMSI of device
 * @property mBssid BSSID of device
 */
data class AssociationData(@SerializedName("imei")
                           val mImei: String?=null,
                           @SerializedName("serialNumber")
                           val mSerialNumber: String?=null,
                           @SerializedName("ssid")
                           val mSsid: String?=null,
                           @SerializedName("iccid")
                           val mIcCid: String?=null,
                           @SerializedName("msisdn")
                           val mMsisdn: String?=null,
                           @SerializedName("imsi")
                           val mImSi: String?=null,
                           @SerializedName("bssid")
                           val mBssid: String?=null)
