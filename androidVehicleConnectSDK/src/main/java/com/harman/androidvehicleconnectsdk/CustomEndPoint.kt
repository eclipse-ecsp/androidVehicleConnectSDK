package com.harman.androidvehicleconnectsdk
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
import com.harman.androidvehicleconnectsdk.network.EndPoint
import com.harman.androidvehicleconnectsdk.network.RequestMethod

/**
 * CustomEndPoint data class is used to set the customized end points for retrofit service
 * This class is implementing EndPoint Interface to get the mandatory functions
 *
 * @property baseUrl holds Base URL of API calls
 * @property path holds path/endpoint for baseUrl of API calls
 * @property method holds request methods of API calls
 * @property header holds Header values of API calls
 * @property body holds body of API calls
 */
data class CustomEndPoint(
    override var baseUrl: String?,
    override var path: String?,
    override var method: RequestMethod?,
    override var header: HashMap<String, String>?,
    override var body: Any?,
) : EndPoint
