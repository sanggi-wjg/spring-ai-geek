rootProject.name = "spring-ai-geek"

include(
    "storage:rds",
    "storage:memorydb",
    "storage:vectordb",
    "application:api",
)
