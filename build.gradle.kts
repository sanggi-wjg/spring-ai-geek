plugins {
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
//    id("org.asciidoctor.jvm.convert") version "3.3.2"
    kotlin("jvm") version "1.9.25"
    kotlin("kapt") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    kotlin("plugin.jpa") version "1.9.25"
}

allprojects {
    group = "com.raynor.demo"
    version = "0.0.1-SNAPSHOT"

    apply {
        plugin("kotlin-noarg")
    }

    repositories {
        mavenCentral()
        maven { url = uri("https://repo.spring.io/milestone") }
        maven { url = uri("https://repo.spring.io/snapshot") }
    }

    noArg {
        annotation("javax.xml.bind.annotation.XmlRootElement")
        annotation("com.raynor.geek.shared.annotations.NoArg")
    }
}

subprojects {
    apply {
        plugin("kotlin")
        plugin("kotlin-kapt")
        plugin("kotlin-spring")
        plugin("kotlin-jpa")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
//        plugin("org.asciidoctor.jvm.convert")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }

    extra["springAiVersion"] = "1.0.0-M5"
    extra["springCloudVersion"] = "2024.0.0"

    dependencyManagement {
        imports {
            mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
        }
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        }
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    kotlin {
        compilerOptions {
            freeCompilerArgs.addAll("-Xjsr305=strict")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
