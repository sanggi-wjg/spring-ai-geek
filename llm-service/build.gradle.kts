import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":shared"))
    implementation(project(":storage:rds"))
    implementation(project(":storage:memorydb"))
    implementation(project(":client"))

    api("org.springframework.ai:spring-ai-ollama-spring-boot-starter")
    api("org.springframework.ai:spring-ai-milvus-store-spring-boot-starter")
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true
