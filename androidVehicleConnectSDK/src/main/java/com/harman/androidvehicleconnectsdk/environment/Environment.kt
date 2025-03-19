package com.harman.androidvehicleconnectsdk.environment
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
 * Data model class to hold the Environment details
 *
 * @property title name of the environment
 * @property clientId  client Id of the specific environment
 * @property clientSecret  Client secret of the specific environment
 * @property baseUrl base url of the specific environment
 * @property profileUrl  user profile url of the specific environment
 * @property signinUrl  sign in base url of the specific environment
 * @property signupUrl  sign Up base url of the specific environment
 * @property redirectUrl  redirect url of the specific environment
 * @property scopes scopes related to specific environment
 */
data class Environment(
    @SerializedName("title") var title: String?,
    @SerializedName("client_Id") var clientId: String?,
    @SerializedName("client_secret") var clientSecret: String?,
    @SerializedName("base_url") var baseUrl: String?,
    @SerializedName("profile_url") var profileUrl: String?,
    @SerializedName("signin_url") var signinUrl: String?,
    @SerializedName("signup_url") var signupUrl: String?,
    @SerializedName("redirect_url") var redirectUrl: String?,
    @SerializedName("scopes") var scopes: ArrayList<String> = arrayListOf(),
) {
    override fun toString(): String {
        return title ?: "Not available"
    }
}
