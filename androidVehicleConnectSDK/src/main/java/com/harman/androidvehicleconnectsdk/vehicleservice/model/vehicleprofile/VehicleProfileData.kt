package com.harman.androidvehicleconnectsdk.vehicleservice.model.vehicleprofile
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
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Vehicle Profile Data class used to hold the vehicle profile details
 *
 * @property message message of API response
 * @property data List of Vehicle Details
 */
@Parcelize
data class VehicleProfileData(
    @SerializedName("message") var message: String,
    @SerializedName("data") var data: List<VehicleDetailData>?,
) : Parcelable

/**
 * This holds the Vehicle in detailed data
 *
 * @property authorizedUsers holds list of User detail
 * @property vehicleAttributes holds vehicle related attribute data
 * @property vin holds vehicle identity number
 * @property vehicleId holds vehicle ID
 * @property dummy
 */
@Parcelize
data class VehicleDetailData(
    @SerializedName("authorizedUsers") var authorizedUsers: List<UserData>?,
    @SerializedName("vehicleAttributes") var vehicleAttributes: VehicleAttributeData?,
    @SerializedName("vin") var vin: String?,
    @SerializedName("vehicleId") var vehicleId: String?,
    @SerializedName("dummy") var dummy: Boolean?,
) : Parcelable

/**
 * Used to hold User roles and id of the particular vehicle
 *
 * @property userId holds user identity
 * @property role holds role
 */
@Parcelize
data class UserData(
    @SerializedName("userId") var userId: String?,
    @SerializedName("role") var role: String?,
) : Parcelable

/**
 * Used to hold the vehicle attribute data
 *
 * @property make make of the vehicle
 * @property model model of the vehicle
 * @property marketingColor marketing color of the vehicle
 * @property baseColor base color of the vehicle
 * @property modelYear model year of the vehicle
 * @property destinationCountry country of the vehicle
 * @property engineType engine Type of the vehicle
 * @property bodyStyle body style of the vehicle
 * @property bodyType body type of the vehicle
 * @property name nick name of the vehicle
 * @property trim trim
 * @property type type of the vehicle
 * @property fuelType fuel type of the vehicle
 */
@Parcelize
data class VehicleAttributeData(
    @SerializedName("make") var make: String?,
    @SerializedName("model") var model: String?,
    @SerializedName("marketingColor") var marketingColor: String?,
    @SerializedName("baseColor") var baseColor: String?,
    @SerializedName("modelYear") var modelYear: String?,
    @SerializedName("destinationCountry") var destinationCountry: String?,
    @SerializedName("engineType") var engineType: String?,
    @SerializedName("bodyStyle") var bodyStyle: String?,
    @SerializedName("bodyType") var bodyType: String?,
    @SerializedName("name") var name: String?,
    @SerializedName("trim") var trim: String?,
    @SerializedName("type") var type: String?,
    @SerializedName("fuelType") var fuelType: String?,
) : Parcelable

/**
 * This holds the vehicle data which is need to update the vehicle profile
 *
 * @property vehicleDetail holds vehicle attribute data
 */
data class PostVehicleAttributeData(
    @SerializedName("vehicleAttributes") var vehicleDetail: VehicleAttributeDetail,
)

/**
 * This holds the vehicle attribute details
 *
 * @property baseColor base color of the vehicle
 * @property make make of the vehicle
 * @property model model of the vehicle
 * @property modelYear model year of the vehicle
 * @property bodyType body type of the vehicle
 * @property name name of the vehicle
 */
data class VehicleAttributeDetail(
    @SerializedName("baseColor")
    var baseColor: String? = null,
    @SerializedName("make")
    var make: String? = null,
    @SerializedName("model")
    var model: String? = null,
    @SerializedName("modelYear")
    var modelYear: String? = null,
    @SerializedName("bodyType")
    var bodyType: String? = null,
    @SerializedName("name")
    var name: String? = null,
)
