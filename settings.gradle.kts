plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "logic-tournament"

// Inclui os teus módulos no projeto
include("backend")
include("games")
include("android-app")
include("common-serialization")
include("common")
