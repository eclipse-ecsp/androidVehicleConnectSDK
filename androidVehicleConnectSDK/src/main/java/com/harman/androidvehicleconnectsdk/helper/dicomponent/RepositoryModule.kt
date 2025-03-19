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
import com.harman.androidvehicleconnectsdk.network.networkmanager.IRetrofitManager
import com.harman.androidvehicleconnectsdk.network.networkmanager.RetrofitManager
import com.harman.androidvehicleconnectsdk.notificationservice.repository.NotificationRepoInterface
import com.harman.androidvehicleconnectsdk.notificationservice.repository.NotificationRepository
import com.harman.androidvehicleconnectsdk.roservice.repository.RoRepository
import com.harman.androidvehicleconnectsdk.roservice.repository.RoRepositoryInterface
import com.harman.androidvehicleconnectsdk.userservice.repository.IUserRepository
import com.harman.androidvehicleconnectsdk.userservice.repository.UserRepository
import com.harman.androidvehicleconnectsdk.vehicleservice.repository.VehicleRepoInterface
import com.harman.androidvehicleconnectsdk.vehicleservice.repository.VehicleRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * RepositoryModule class has functions related to the classes need to get injected
 *
 */
@Module
class RepositoryModule {
    /**
     * This function is to provide the VehicleRepoInterface instance for injection
     *
     * @return VehicleRepoInterface instance using VehicleRepository class
     */
    @Singleton
    @Provides
    fun provideVehicleServiceRepo(): VehicleRepoInterface {
        return VehicleRepository()
    }

    /**
     * This function is to provide the IRetrofitManager instance for injection
     *
     * @return IRetrofitManager instance using RetrofitManager class
     */
    @Singleton
    @Provides
    fun provideRetrofitManager(): IRetrofitManager {
        return RetrofitManager()
    }

    /**
     * This function is to provide the RoRepositoryInterface instance for injection
     *
     * @return RoRepositoryInterface instance using RoRepository class
     */
    @Singleton
    @Provides
    fun provideRoRepository(): RoRepositoryInterface {
        return RoRepository()
    }

    /**
     * This function is to provide the IUserRepository instance for injection
     *
     * @return IUserRepository instance using UserRepository class
     */
    @Singleton
    @Provides
    fun provideUserRepository(): IUserRepository {
        return UserRepository()
    }

    /**
     * This function is to provide the NotificationRepoInterface instance for injection
     *
     * @return NotificationRepoInterface instance using NotificationRepository class
     */
    @Singleton
    @Provides
    fun provideNotificationRepository(): NotificationRepoInterface {
        return NotificationRepository()
    }
}
