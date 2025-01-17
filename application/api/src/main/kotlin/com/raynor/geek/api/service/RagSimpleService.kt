package com.raynor.geek.api.service

import com.raynor.geek.api.repository.GeekVectorRepository
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.ai.ollama.OllamaChatModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class RagSimpleService(
    private val geekVectorRepository: GeekVectorRepository,
    private val llm: OllamaChatModel,
) {
    @Value("classpath:prompts/qa-1.st")
    lateinit var qaPrompt: Resource

    fun simple(): ChatResponse {
//        addSimpleDocuments()
        val userInput = "봄비"
        val foundDocuments = geekVectorRepository.similaritySearch(userInput, 3)

        val prompt = PromptTemplate(
            qaPrompt,
            mapOf(
                "documents" to foundDocuments.mapNotNull { it.text }.joinToString("\n"),
                "question" to userInput
            )
        ).create()

        return llm.call(prompt)
    }

//    fun simple2(): ChatResponse {
//        addSimpleDocuments()
//
//        return ChatClient.builder(llm)
//            .build()
//            .prompt(SystemPromptTemplate(qaPrompt).template)
//            .advisors(geekVectorRepository.getQaAdvisor())
//            .user("겨울 날씨")
//            .call()
//            .chatResponse()
//            ?: throw Exception("ChatResponse is null")
//    }
//
//    private fun addSimpleDocuments() {
//        val documents = listOf(
//            Document("봄비가 많이 오면 아낙네 손이 커진다: 봄에 비가 많이 오면 농작물이 잘 커서 풍년이 들게 되므로 씀씀이가 커진다."),
//            Document("봄바람에 말똥 굴러가듯 한다: 봄바람에 마른 말똥이 잘 굴러가듯이 일이 잘 풀린다."),
//            Document("봄 추위가 장독 깬다: 따뜻한 봄날에도 의외로 사나운 추위가 있다."),
//            Document("개구리가 울면 비가 온다: 저기압이 형성되면 습도가 높아지는데 개구리는 먼저 그 느낌을 감지하고 평소보다 많이 운다."),
//            Document("장마 끝물의 참외는 거저 줘도 안 먹는다: 장마 때는 비가 많이 오기 때문에 과일들은 태양빛을 받지 못해 당도가 떨어진다"),
//            Document("가을 안개는 풍년 든다: 일반적으로 안개는 날씨가 좋은 날 자주 생기게 마련이지만, 가을철에 안개가 자주 끼는 날은 맑은 날씨인 경우가 많아 곡식이 잘 영근다."),
//            Document("동짓날이 추워야 풍년든다.: 추워야 병충해가 얼어죽기 때문에 풍년이 든다는 것 "),
//        )
//        geekVectorRepository.addDocuments(documents)
//    }
}