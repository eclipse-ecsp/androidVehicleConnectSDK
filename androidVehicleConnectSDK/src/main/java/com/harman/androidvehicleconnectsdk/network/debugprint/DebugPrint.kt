package com.harman.androidvehicleconnectsdk.network.debugprint

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
 * DebugPrint class is used to print the SDK Logs
 *
 */
object DebugPrint {
    private var iLogger: IDebugPrint? = null

    /**
     * This function is to set the DebugPrintLogger instance
     *
     * @param logger IDebugPrint instance is passed here
     */
    fun setLogger(logger: IDebugPrint?) {
        if (logger != null) {
            iLogger = logger
        }
    }

    /**
     * This function is to print the debug logs
     *
     * @param callerTag TAG is used to print the log
     * @param message Value which is need to log
     */
    fun d(callerTag: String?, message: Any?) {
        if (iLogger != null) {
            iLogger!!.d("SDK_$callerTag", message)
        }
    }

    /**
     * This function is to print the Error logs
     *
     * @param callerTag TAG is used to print the log
     * @param message Value which is need to log
     */
    fun e(callerTag: String?, message: Any?) {
        if (iLogger != null) {
            iLogger!!.e("SDK_$callerTag", message)
        }
    }
}