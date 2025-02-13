pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

buildCache {
    local {
        directory = File(rootDir, "build-cache")
    }
}

rootProject.name = "androidVehicleConnectSDK"

include(":app")
include(":androidVehicleConnectSDK")
