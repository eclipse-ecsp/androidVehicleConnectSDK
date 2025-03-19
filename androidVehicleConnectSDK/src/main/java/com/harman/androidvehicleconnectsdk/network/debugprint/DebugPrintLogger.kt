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
import android.util.Log

/**
 * DebugPrintLogger class is used to log the TAG and message to android.util log with the thread which is running
 *
 */
class DebugPrintLogger : IDebugPrint {
    companion object {
        private const val SPACE_STRING = " "
    }

    /**
     * This function is to log the error part
     *
     * @param tag TAG is used to print the log
     * @param msg Value which is need to log
     */
    private fun e(
        tag: String?,
        msg: String,
    ) {
        val message = messageWithThreadId(msg)
        Log.e(tag, message)
    }

    /**
     * This function is to log the debug part
     *
     * @param tag TAG is used to print the log
     * @param msg Value which is need to log
     */
    private fun debug(
        tag: String?,
        msg: String,
    ) {
        val message = messageWithThreadId(msg)
        Log.d(tag, message)
    }

    /**
     * This function is to attach the thread (task is running) to message
     *
     * @param msg string value of log
     * @return thread attached log message
     */
    private fun messageWithThreadId(msg: String): String {
        val sb = StringBuilder()
        rightJustifiedNumber(sb, Thread.currentThread().id)
        sb.append(SPACE_STRING)
        sb.append(msg)
        return sb.toString()
    }

    /**
     * This function is to append the thread
     *
     * @param sb StringBuilder which is send by messageWithThreadId function
     * @param ll Thread value
     */
    private fun rightJustifiedNumber(
        sb: java.lang.StringBuilder,
        ll: Long,
    ) {
        if (ll < 100000) sb.append(SPACE_STRING)
        if (ll < 10000) sb.append(SPACE_STRING)
        if (ll < 1000) sb.append(SPACE_STRING)
        if (ll < 100) sb.append(SPACE_STRING)
        if (ll < 10) sb.append(SPACE_STRING)
        sb.append(ll)
    }

    /**
     * This function is to provide the debug message to debug function
     *
     * @param callerTag TAG is used to print the log
     * @param message Value which is need to log
     */
    override fun d(
        callerTag: String?,
        message: Any?,
    ) {
        debug(callerTag, message.toString())
    }

    /**
     * This function is to provide the error message to debug function
     *
     * @param callerTag TAG is used to print the log
     * @param message Value which is need to log
     */
    override fun e(
        callerTag: String?,
        message: Any?,
    ) {
        if (message != null) {
            e(callerTag, message.toString())
        }
    }
}
