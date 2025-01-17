package com.raynor.geek.api.controller

import com.raynor.geek.api.controller.dto.request.SearchRequestDto
import com.raynor.geek.api.service.SearchService
import jakarta.validation.Valid
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/search")
class SearchAssistantController(
    private val searchService: SearchService,
) {

    @PostMapping("")
    fun search(
        @RequestBody @Valid searchRequestDto: SearchRequestDto
    ): ChatResponse {
        return searchService.search(searchRequestDto.query)
    }

    @PostMapping("/vector")
    fun searchFromVector(
        @RequestBody @Valid searchRequestDto: SearchRequestDto
    ): ChatResponse {
        return searchService.searchFromVector(searchRequestDto.query)
    }
}
