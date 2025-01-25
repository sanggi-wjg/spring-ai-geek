package com.raynor.geek.api.controller

import com.raynor.geek.api.controller.dto.request.SearchRequestDto
import com.raynor.geek.llmservice.service.SearchingService
import jakarta.validation.Valid
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/searching-assistant")
class SearchingAssistantController(
    private val searchingService: SearchingService,
) {

    @PostMapping("/web")
    fun searchWeb(
        @Valid @RequestBody searchRequestDto: SearchRequestDto
    ): ChatResponse {
        return searchingService.searchWeb(searchRequestDto.query, searchRequestDto.llmParameter)
    }

    @PostMapping("/news")
    fun searchNews(
        @Valid @RequestBody searchRequestDto: SearchRequestDto
    ): ChatResponse {
        return searchingService.searchNews(searchRequestDto.query, searchRequestDto.llmParameter)
    }

    @PostMapping("/vector")
    fun searchVector(
        @Valid @RequestBody searchRequestDto: SearchRequestDto
    ): ChatResponse {
        return searchingService.searchVector(searchRequestDto.query, searchRequestDto.llmParameter)
    }
}
