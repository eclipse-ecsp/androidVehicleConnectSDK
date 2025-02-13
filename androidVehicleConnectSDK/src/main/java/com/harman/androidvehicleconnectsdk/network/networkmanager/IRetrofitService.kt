package com.harman.androidvehicleconnectsdk.network.networkmanager

import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Url

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
 * IRetrofitService interface is used by the retrofit service
 *
 */
interface IRetrofitService {
    /**
     * This function is to do the GET request
     *
     * @param endPointPath end point of the API request URL
     * @return Response<JsonElement> object as result of API trigger
     */
    @GET
    suspend fun getRequest(@Url endPointPath: String): Response<JsonElement>

    /**
     * This function is to do the POST request without the body to retrofit
     *
     * @param endPointPath end point of the API request URL
     * @return Response<JsonElement> object as result of API trigger
     */
    @POST
    suspend fun postRequestWithOutBody(
        @Url endPointPath: String
    ): Response<JsonElement>

    /**
     * This function is to do the POST request with the body to retrofit
     *
     * @param endPointPath end point of the API request URL
     * @param data Body part of the request
     * @return Response<JsonElement> object as result of API trigger
     */
    @POST
    suspend fun  postRequestWithBody(
        @Url endPointPath: String,
        @Body data: Any? = null,
    ): Response<JsonElement>

    /**
     * This function is to do the PUT request to retrofit
     *
     * @param endPointPath end point of the API request URL
     * @param data Body part of the request
     * @return Response<JsonElement> object as result of API trigger
     */
    @PUT
    suspend fun  putRequest(
        @Url endPointPath: String,
        @Body data: Any? = null
    ): Response<JsonElement>

    /**
     * This function is to do the deletion request to retrofit
     *
     * @param endPointPath end point of the API request URL
     * @param data Body part of the request
     * @return Response<JsonElement> object as result of API trigger
     */
    @DELETE
    suspend fun deleteRequest(
        @Url endPointPath: String,
        @Body data: Any? = null
    ): Response<JsonElement>

    /**
     * This function is to do PATCH request to retrofit
     *
     * @param endPointPath end point of the API request URL
     * @param data Body part of the request
     * @return Response<JsonElement> object as result of API trigger
     */
    @PATCH
    suspend fun patchRequest(
        @Url endPointPath: String,
        @Body data: Any? = null
    ): Response<JsonElement>
}