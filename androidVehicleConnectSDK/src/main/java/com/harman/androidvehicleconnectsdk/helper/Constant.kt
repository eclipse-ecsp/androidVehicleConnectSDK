package com.harman.androidvehicleconnectsdk.helper

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
object Constant {
//General constants
 const val ACTION_KEY = "action_key"
 const val SIGN_IN = "sign_in"
 const val SIGN_UP = "sign_up"
 const val SIGN_OUT = "sign_out"

 const val CLIENT_SECRET    = "client_secret"
 const val HEADER_AUTHORIZATION = "Authorization"
 const val HEADER_ACCEPT_LANGUAGE = "Accept-Language"
 const val HEADER_APPLICATION_JSON = "application/json"
 const val HEADER_ACCEPT = "Accept"
 const val HEADER_CONTENT_TYPE_KEY = "Content-Type"
 const val HEADER_CONTENT_TYPE_VALUE = "application/x-www-form-urlencoded"
 const val GRANT_TYPE_KEY = "grant_type"
 const val GRANT_TYPE_VALUE = "refresh_token"
 const val CUSTOM_MESSAGE_VALUE = "custom_message_intent_value"
 const val REQUEST_CODE = "request_code"
 const val ORIGIN_KEY = "OriginId"
 const val ORIGIN_VALUE = "THIRDPARTY2"
 const val REQUEST_ID = "RequestId"
 const val SESSION_ID = "SessionId"


 const val USER_ID = "{userId}"
 const val VEHICLE_ID = "{vehicleId}"
 const val CONTACT_ID = "{contactId}"
 const val DEVICE_ID = "{deviceId}"
 const val ALERT_TYPE = "{alertTypes}"
 const val CONTACT_SELF = "self"

 //NETWORK ERRORS
 const val UNAUTHORIZED_ERROR        = "Unauthorized error"

 //GENERIC ERRORS

 const val ENVIRONMENT_NOT_CONFIGURED = "Environment not configured"
 const val REFRESH_TOKEN_FAILED_ERROR = "We have reached maximum retry for refresh token. Therefore clear the token."
 const val INVALID_REQUEST            = "Invalid request"
 const val WRONG_INTENT            = "Wrong Intent action"
 const val SERVER_ERROR            = "Internal Server error"

}
