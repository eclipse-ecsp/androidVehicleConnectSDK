package org.eclipse.ecsp.roservice.model

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
sealed class RemoteOperationState(val state: String? = null, val roEndPoint: String) {
    /**
     * RemoteOperationState sealed class is used to hold the values of Remote operation state
     *
     * @property state RO state value
     * @property roEndPoint base url end point
     */
    data object AlarmOn : RemoteOperationState("ON", "alarm")

    data object AlarmOff : RemoteOperationState("OFF", "alarm")

    data object WindowOpen : RemoteOperationState("OPENED", "windows")

    data object WindowClose : RemoteOperationState("CLOSED", "windows")

    data object WindowsAjar : RemoteOperationState("PARTIAL_OPENED", "windows")

    data object LightsOn : RemoteOperationState("ON", "lights")

    data object LightsOff : RemoteOperationState("OFF", "lights")

    data object LightsFlash : RemoteOperationState("FLASH", "lights")

    data object TrunkLocked : RemoteOperationState("LOCKED", "trunk")

    data object TrunkUnLocked : RemoteOperationState("UNLOCKED", "trunk")

    data object EngineStart : RemoteOperationState("STARTED", "engine")

    data object EngineStop : RemoteOperationState("STOPPED", "engine")

    data object DoorsLocked : RemoteOperationState("LOCKED", "doors")

    data object DoorsUnLocked : RemoteOperationState("UNLOCKED", "doors")
}
