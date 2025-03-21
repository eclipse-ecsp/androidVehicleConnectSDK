package org.eclipse.ecsp.roservice.model
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
 * RoRequestData data class is used to hold the RO request data
 *
 * @property mState represents the state of RO
 * @property mPercent represents the percent of state - optional for some state
 * @property mDuration represents the time duration of state - optional for some state
 */
data class RoRequestData(
    @SerializedName("state") var mState: String,
    @SerializedName("percent") var mPercent: Int? = null,
    @SerializedName("duration") var mDuration: Int? = null,
)
