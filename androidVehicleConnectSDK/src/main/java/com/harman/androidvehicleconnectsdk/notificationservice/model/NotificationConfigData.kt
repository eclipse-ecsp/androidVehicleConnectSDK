package com.harman.androidvehicleconnectsdk.notificationservice.model

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
data class NotificationConfigData(@SerializedName("channels") var channels: ArrayList<ChannelData>,
                                  @SerializedName("enabled") var enabled: Boolean,
                                  @SerializedName("group") var group: String? = "all"
)
data class ChannelData(@SerializedName("deviceTokens") var deviceTokens: ArrayList<String>,
                       @SerializedName("enabled") var enabled: Boolean,
                       @SerializedName("type") var type: String? = "push",
                       @SerializedName("service") var service: String? = "gcm",
                       @SerializedName("appPlatform") var appPlatform: String? = "android")
