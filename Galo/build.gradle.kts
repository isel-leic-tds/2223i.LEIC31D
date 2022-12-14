import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    application
}

group = "pt.isel.tds"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Mongo
    implementation("org.litote.kmongo:kmongo:4.7.2")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.17.1")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}

tasks.jar {
    manifest.attributes["Main-Class"] = "pt.isel.tds.ttt.MainKt"
    val dependencies = configurations
        .runtimeClasspath
        .get()
        .map(::zipTree) // OR .map { zipTree(it) }
    from(dependencies)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
