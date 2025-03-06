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
/**
 * Represents the NotificationConfigData model class to configure the notification details
 *
 * @property channels list of channel data
 * @property enabled boolean value
 * @property group by default it is "all"
 */
data class NotificationConfigData(@SerializedName("channels") var channels: ArrayList<ChannelData>,
                                  @SerializedName("enabled") var enabled: Boolean,
                                  @SerializedName("group") var group: String? = "all"
)

/**
 * Represents the ChannelData model class
 *
 * @property deviceTokens holds firebase device token
 * @property enabled boolean value
 * @property type by default type is "push"
 * @property service by default service is "gcm"
 * @property appPlatform by default appPlatform is "android"
 */
data class ChannelData(@SerializedName("deviceTokens") var deviceTokens: ArrayList<String>,
                       @SerializedName("enabled") var enabled: Boolean,
                       @SerializedName("type") var type: String? = "push",
                       @SerializedName("service") var service: String? = "gcm",
                       @SerializedName("appPlatform") var appPlatform: String? = "android")
