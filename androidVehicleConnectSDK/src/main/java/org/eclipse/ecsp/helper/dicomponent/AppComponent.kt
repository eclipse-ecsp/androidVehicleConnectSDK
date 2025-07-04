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
import dagger.BindsInstance
import dagger.Component
import org.eclipse.ecsp.notificationservice.repository.NotificationRepository
import org.eclipse.ecsp.notificationservice.service.NotificationService
import org.eclipse.ecsp.roservice.repository.RoRepository
import org.eclipse.ecsp.roservice.service.RoService
import org.eclipse.ecsp.userservice.repository.UserRepository
import org.eclipse.ecsp.userservice.service.UserService
import org.eclipse.ecsp.vehicleservice.repository.VehicleRepository
import org.eclipse.ecsp.vehicleservice.service.VehicleService
import javax.inject.Singleton

/**
 * AppComponent interface required for dependency injection
 * Modules are declared and injection functions are available
 */
@Singleton
@Component(modules = [ContextModule::class, RepositoryModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
        ): AppComponent
    }

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
