import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":shared"))
    implementation(project(":storage:rds"))
    implementation(project(":storage:memorydb"))
    implementation(project(":client"))
    implementation(project(":llm-service"))

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // csv
    implementation("com.opencsv:opencsv:5.8")
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = true
jar.enabled = false
