package com.harman.androidvehicleconnectsdk.helper.dicomponent

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
import com.harman.androidvehicleconnectsdk.notificationservice.repository.NotificationRepository
import com.harman.androidvehicleconnectsdk.notificationservice.service.NotificationService
import com.harman.androidvehicleconnectsdk.roservice.repository.RoRepository
import com.harman.androidvehicleconnectsdk.roservice.service.RoService
import com.harman.androidvehicleconnectsdk.userservice.repository.UserRepository
import com.harman.androidvehicleconnectsdk.userservice.service.UserService
import com.harman.androidvehicleconnectsdk.vehicleservice.repository.VehicleRepository
import com.harman.androidvehicleconnectsdk.vehicleservice.service.VehicleService
import dagger.Component
import javax.inject.Singleton

/**
 * AppComponent interface required for dependency injection
 * Modules are declared and injection functions are available
 */
@Singleton
@Component(modules = [ContextModule::class, RepositoryModule::class])
interface AppComponent {
    /**
     * Dependency injection for VehicleService class
     *
     * @param vehicleService instance of VehicleService need to pass
     */
    fun inject(vehicleService: VehicleService)

    /**
     * Dependency injection for UserService class
     *
     * @param userService instance of UserService need to pass
     */
    fun inject(userService: UserService)

    /**
     * Dependency injection for VehicleRepository class
     *
     * @param vehicleRepository instance of VehicleRepository need to pass
     */
    fun inject(vehicleRepository: VehicleRepository)

    /**
     * Dependency injection for RoService class
     *
     * @param roService instance of RoService need to pass
     */
    fun inject(roService: RoService)

    /**
     * Dependency injection for RoRepository class
     *
     * @param roRepository instance of RoRepository need to pass
     */
    fun inject(roRepository: RoRepository)

    /**
     * Dependency injection for UserRepository class
     *
     * @param userRepository instance of UserRepository need to pass
     */
    fun inject(userRepository: UserRepository)

    /**
     * Dependency injection for NotificationRepository class
     *
     * @param notificationRepository instance of NotificationRepository need to pass
     */
    fun inject(notificationRepository: NotificationRepository)

    /**
     * Dependency injection for NotificationService class
     *
     * @param notificationService instance of NotificationService need to pass
     */
    fun inject(notificationService: NotificationService)
}