import org.springframework.boot.gradle.tasks.bundling.BootJar

//extra["snippetsDir"] = file("build/generated-snippets")

//configurations {
//    asciidoctorExt
//}

dependencies {
//    compileOnly("org.springframework.boot:spring-boot-starter-test")
//    compileOnly("org.springframework.boot:spring-boot-starter-test")
//    api("org.springframework.restdocs:spring-restdocs-mockmvc")
//    api("org.springframework.restdocs:spring-restdocs-restassured")

//    api("io.rest-assured:spring-mock-mvc")
//    runtimeOnly("org.junit.platform:junit-platform-launcher")
}

//tasks.test {
//    outputs.dir(project.extra["snippetsDir"]!!)
//}

//tasks.asciidoctor {
//    inputs.dir(project.extra["snippetsDir"]!!)
//    dependsOn(tasks.test)
//}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true
