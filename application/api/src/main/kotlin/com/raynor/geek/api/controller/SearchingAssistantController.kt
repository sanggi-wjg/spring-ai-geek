package com.raynor.geek.api.controller

import com.raynor.geek.api.controller.dto.request.SearchRequestDto
import com.raynor.geek.llm.service.SearchingService
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

    @PostMapping("")
    fun search(
        @Valid @RequestBody searchRequestDto: SearchRequestDto
    ): ChatResponse {
        return searchingService.search(searchRequestDto.query)
    }

    @PostMapping("/vector")
    fun searchFromVector(
        @Valid @RequestBody searchRequestDto: SearchRequestDto
    ): ChatResponse {
        return searchingService.searchFromVector(searchRequestDto.query)
    }
}
