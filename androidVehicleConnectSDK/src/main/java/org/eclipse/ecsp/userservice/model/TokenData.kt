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
 * TokenData data class to hold the values of token details
 * this is used to hold response of refresh token API
 *
 * @property mAccessToken Access token value
 * @property mRefreshToken Refresh Token value
 * @property mScope Scope value
 * @property mIdToken token id value
 * @property mTokenType Token type value
 * @property mExpiresIn Expire time of token value
 */
data class TokenData(
    @SerializedName("access_token")
    val mAccessToken: String? = null,
    @SerializedName("refresh_token")
    val mRefreshToken: String? = null,
    @SerializedName("scope")
    val mScope: String? = null,
    @SerializedName("id_token")
    val mIdToken: String? = null,
    @SerializedName("token_type")
    val mTokenType: String? = null,
    @SerializedName("expires_in")
    var mExpiresIn: Long = 0,
)
