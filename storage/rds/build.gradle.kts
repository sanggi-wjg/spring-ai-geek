import org.springframework.boot.gradle.tasks.bundling.BootJar

//allOpen {
//    annotation("jakarta.persistence.Entity")
//    annotation("jakarta.persistence.MappedSuperclass")
//    annotation("jakarta.persistence.Embeddable")
//}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    api("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("com.github.f4b6a3:ulid-creator:5.2.3")

    runtimeOnly("org.postgresql:postgresql")
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true
