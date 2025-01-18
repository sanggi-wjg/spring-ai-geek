rootProject.name = "spring-ai-geek"

include(
    "shared",
    "storage:rds",
    "storage:memorydb",
    "client",
    "application:llm",
    "application:api",
)
