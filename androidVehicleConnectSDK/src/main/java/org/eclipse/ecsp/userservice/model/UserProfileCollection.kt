package org.eclipse.ecsp.userservice.model
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
 * UserProfileCollection data class is used to hold the User profile data response
 *
 * @property mMessages key value from API response
 * @property userProfileList List of User profile data class
 */
data class UserProfileCollection(
    @SerializedName("messages") val mMessages: List<Message>,
    @SerializedName("results") val userProfileList: List<UserProfile>,
) {
    /**
     * Holds the Message values which contains key as string
     *
     * @property mKey holds the string value
     */
    data class Message(
        @SerializedName("key") val mKey: String,
    )
}

/**
 * UserProfile data class holds the User profile detailed values
 *
 * @property mId user id
 * @property mUserName user name
 * @property mFirstName User first name
 * @property mLastName User Last name
 * @property mRole User Roles list
 * @property mStatus User status
 * @property mEmail User email ID
 * @property mPhoneNumber user phone Number
 * @property mLocale User locale value
 */
data class UserProfile(
    @SerializedName("id") val mId: String,
    @SerializedName("userName") val mUserName: String,
    @SerializedName("firstName") val mFirstName: String?,
    @SerializedName("lastName") val mLastName: String?,
    @SerializedName("role") val mRole: List<String>,
    @SerializedName("status") val mStatus: String,
    @SerializedName("email") val mEmail: String,
    @SerializedName("phoneNumber") val mPhoneNumber: String?,
    @SerializedName("locale") val mLocale: String?,
)
