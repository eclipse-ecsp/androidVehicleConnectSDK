package org.eclipse.ecsp.network

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
interface EndPoint {
    /**
     * Represents the base url used by the API
     */
    var baseUrl: String?

    /**
     * Represents the path used by the API
     */
    var path: String?

    /**
     * Represents the API method
     */
    var method: RequestMethod?

    /**
     * Represents the API header
     */
    var header: HashMap<String, String>?

    /**
     * Represents the API body
     */
    var body: Any?
}
