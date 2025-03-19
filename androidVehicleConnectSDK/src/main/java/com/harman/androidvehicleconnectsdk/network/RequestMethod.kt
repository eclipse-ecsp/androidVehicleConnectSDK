package com.harman.androidvehicleconnectsdk.network

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
sealed class RequestMethod(val method: String) {
    /**
     * Delete - method used to call delete API
     */
    data object Delete : RequestMethod("DELETE")

    /**
     * Get - method used to call Get API
     */
    data object Get : RequestMethod("GET")

    /**
     * Patch - method used to call Patch API
     */
    data object Patch : RequestMethod("PATCH")

    /**
     * Post - method used to call Post API
     */
    data object Post : RequestMethod("POST")

    /**
     * Put - method used to call Put API
     */
    data object Put : RequestMethod("PUT")
}
