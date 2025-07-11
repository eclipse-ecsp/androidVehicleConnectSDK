package org.eclipse.ecsp.helper.dicomponent
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
import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * ContextModule class is a module class for dependency injection
 * Used to inject the context of the application
 */
@Module
class ContextModule {
    /**
     * This function is to provide the Context of application which is received using dependency injection
     *
     * @param application holds the instance of [Application]
     * @return Context of the application
     */
    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }
}
