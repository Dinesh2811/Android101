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
        maven("https://jitpack.io")
    }
}

rootProject.name = "Android101"
include(":app")

include(":old:temp")
include(":old:basic")
include(":old:compose")
include(":old:di:app")
include(":old:di:dagger")
include(":old:di:hilt")

include(":lib:theme")
