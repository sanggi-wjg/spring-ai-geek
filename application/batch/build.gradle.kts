import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":shared"))
    implementation(project(":storage:rds"))
    implementation(project(":storage:memorydb"))
//    implementation(project(":client"))
    implementation(project(":llm-service"))
    
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true
