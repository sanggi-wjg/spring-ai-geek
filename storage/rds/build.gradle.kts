import org.springframework.boot.gradle.tasks.bundling.BootJar

//allOpen {
//    annotation("jakarta.persistence.Entity")
//    annotation("jakarta.persistence.MappedSuperclass")
//    annotation("jakarta.persistence.Embeddable")
//}

dependencies {
    implementation(project(":shared"))

    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")

    // querydsl
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    implementation("com.querydsl:querydsl-core:5.0.0")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")

    // ULID
    implementation("com.github.f4b6a3:ulid-creator:5.2.3")
}

sourceSets {
    main {
        java {
            srcDirs("src/main/kotlin")
        }
    }
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true
