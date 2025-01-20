rootProject.name = "spring-ai-geek"

include(
    "shared",
    "storage:rds",
    "storage:memorydb",
    "client",
    "llm-service",
    "application:api",
    "application:batch",
    "tests:rest-docs"
)
