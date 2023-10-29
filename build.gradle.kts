// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
}

buildscript {

    val agp_version by extra("8.1.1")
    repositories {
    }

    dependencies {
        classpath("com.android.tools.build:gradle:$agp_version")
        classpath("com.google.gms:google-services:4.4.0")
    }

}
val defaultVersionCode by extra(1)


allprojects {
    tasks.withType<JavaCompile> {
        options.compilerArgs.add("-Xlint:deprecation")
        options.compilerArgs.add("-Xlint:unchecked")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}