plugins {
    java
    idea
    kotlin("jvm") version "2.0.21"
    jacoco
    id("jacoco-report-aggregation")
    application

}

group = "com.robofinance.native.wf"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val LOGBACK_CLASSIC = "1.5.13"
val LOGSTASH_LOGBACK_ENCODER = "8.0"
val MOCKITO_KOTLIN = "4.0.0"
val MICROMETER_PROMETHEUS = "1.13.1"
val JUNIT = "5.8.1"
val JUNIT_PIONEER = "2.3.0"
val MOCKK = "1.13.12"

allprojects {
    apply {
        plugin("java")
        plugin("kotlin")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("jacoco")
    }
    java.targetCompatibility = JavaVersion.VERSION_21
    java.sourceCompatibility = JavaVersion.VERSION_21

    dependencies {
        implementation(kotlin("stdlib"))
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("ch.qos.logback:logback-classic:$LOGBACK_CLASSIC")
        implementation("net.logstash.logback:logstash-logback-encoder:$LOGSTASH_LOGBACK_ENCODER")
        implementation("io.micrometer:micrometer-registry-prometheus:$MICROMETER_PROMETHEUS")
        testImplementation("org.mockito.kotlin:mockito-kotlin:$MOCKITO_KOTLIN")
        testImplementation("org.junit.jupiter:junit-jupiter:$JUNIT")
        testImplementation("org.junit-pioneer:junit-pioneer:$JUNIT_PIONEER")
        testImplementation("io.mockk:mockk:$MOCKK")

    }
}
dependencies {
    implementation(kotlin("stdlib"))
}


tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

tasks.jar {
    enabled = true
    archiveFileName.set("app.jar")
    manifest {
        attributes(
            "Main-Class" to "com.example.wf.MainKt"
        )
    }
}
    // возможно нахой не надо
application {
    mainClass.set("com.robofinance.wf.MainKt")
}
tasks.check {
    dependsOn(tasks.named<JacocoReport>("testCodeCoverageReport"))
}
