package com.harman.androidvehicleconnectsdk.network.networkmanager

import com.google.gson.JsonElement
import com.harman.androidvehicleconnectsdk.network.EndPoint
import com.harman.androidvehicleconnectsdk.network.RequestMethod
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

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
 * RetrofitManager class is used to do the generic request call to retrofit service
 *
 */
@Singleton
class RetrofitManager @Inject constructor() : IRetrofitManager {
    /**
     * This function is used to send the request to to retrofit service
     *
     * @param endPoint EndPoint interface which holds the necessary data to do retrofit call
     * @return Response<JsonObject> object as result of API trigger
     */
    override suspend fun sendRequest(endPoint: EndPoint): Response<JsonElement>? {
        val retrofitService =
            RetrofitProvider.getRetrofitClient(endPoint)?.create(IRetrofitService::class.java)
        return when (endPoint.method) {
            RequestMethod.Get ->  retrofitService?.getRequest(endPoint.path?: "")
            RequestMethod.Post -> {
                if (endPoint.body == null) retrofitService?.postRequestWithOutBody(endPoint.path?: "")
                else retrofitService?.postRequestWithBody(endPoint.path?: "", endPoint.body)
            }

            RequestMethod.Put -> retrofitService?.putRequest(endPoint.path?: "", endPoint.body)
            RequestMethod.Patch -> retrofitService?.patchRequest(endPoint.path?: "", endPoint.body)
            RequestMethod.Delete -> retrofitService?.deleteRequest(endPoint.path?: "", endPoint.body)
            else -> retrofitService?.getRequest(endPoint.path?: "")
        }
    }
}

/**
 * IRetrofitManager interface which is implemented by RetrofitManager class
 *
 */
interface IRetrofitManager {
    /**
     * function represent to send the API request
     *
     * @param endPoint
     * @return Response<JsonElement> as result
     */
    suspend fun sendRequest(endPoint: EndPoint): Response<JsonElement>?

    companion object {
        /**
         * Used to initialize the RetrofitManager
         *
         * @return IRetrofitManager interface object
         */
        fun getRetrofitManager(): IRetrofitManager = RetrofitManager()
    }
}