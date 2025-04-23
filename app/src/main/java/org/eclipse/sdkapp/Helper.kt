package org.eclipse.sdkapp

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


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

internal inline fun <reified T> Gson.dataToJson(data: T): String = toJson(data)

/**
 * This function is used to convert json string to Respective data class
 *
 * @param T Generic data class
 * @param json json String as input
 * @return Respective data class object
 */
internal inline fun <reified T> Gson.fromJson(json: String): T =
    fromJson(json, object : TypeToken<T>() {}.type)

const val BASE_URL = "https://localhost.com"
const val DUMMY_USER = "new_dummy_user@yopmail.com"
const val DUMMY_VALUE = "dummy"
