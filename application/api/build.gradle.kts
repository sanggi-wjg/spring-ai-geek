import org.springframework.boot.gradle.tasks.bundling.BootJar

val snippetsDir = file("build/generated-snippets")
val buildDocsDir = file("build/docs/asciidoc")
val staticDir = file("src/main/resources/static/docs")

val asciidoctorExt: Configuration by configurations.creating {
    extendsFrom(configurations["testImplementation"])
}

configurations {
    asciidoctorExt
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":storage:rds"))
    implementation(project(":storage:memorydb"))
    implementation(project(":application:llm"))

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
//    implementation("org.springframework.boot:spring-boot-devtools")

    asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.springframework.restdocs:spring-restdocs-restassured")
}

tasks.test {
    outputs.dir(snippetsDir)
    useJUnitPlatform()
}

tasks.asciidoctor {
    dependsOn(tasks.test)
    configurations("asciidoctorExt")
    baseDirFollowsSourceFile()

    doFirst {
        delete(staticDir)
    }

    inputs.dir(snippetsDir)

    doLast {
        copy {
            from(buildDocsDir)
            to(staticDir)
        }
    }
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = true
jar.enabled = false
