package com.raynor.geek.llmservice.config

//@Configuration
//class LlmConfig(
//    private val chatModel: OllamaChatModel,
//) {
//
//    @Bean
//    fun chatClient() =
//        ChatClient.builder(chatModel)
//            .defaultAdvisors(
//                VectorStoreChatMemoryAdvisor
//                MessageChatMemoryAdvisor(InMemoryChatMemory(), "llm", 50)
//            )
//            .build()
//}