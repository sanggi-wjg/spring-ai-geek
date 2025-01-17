import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":storage:rds"))
    implementation(project(":storage:memorydb"))
    implementation(project(":client"))

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.ai:spring-ai-ollama-spring-boot-starter")
    implementation("org.springframework.ai:spring-ai-milvus-store-spring-boot-starter")

    implementation("org.springframework.boot:spring-boot-devtools")
}


val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = true
jar.enabled = false
