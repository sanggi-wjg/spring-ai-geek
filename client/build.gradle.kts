import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":shared"))

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("io.github.openfeign:feign-hc5")
//    implementation("io.github.openfeign:feign-micrometer")
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true
