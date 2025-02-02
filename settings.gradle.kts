pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "CoinViewer"

include(":app")

include(":presentation")
include(":feature:splash")
include(":feature:home")
include(":feature:coin-detail")
include(":feature:setting")

include(":core:domain")
include(":core:data")
include(":core:common-ui")
include(":core:navigation")
include(":core:i18n")
