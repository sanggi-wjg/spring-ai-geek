package com.raynor.geek.api.controller

import com.raynor.geek.api.controller.dto.request.WritingRequestDto
import com.raynor.geek.llm.service.WritingService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/writing-assistant")
class WritingAssistantController(
    private val writingService: WritingService,
) {

    @PostMapping("")
    fun helpWriting(
        @Valid @RequestBody writingRequestDto: WritingRequestDto,
    ): ResponseEntity<String> {
        return writingService.helpWriting(writingRequestDto.text).let {
            ResponseEntity.ok(it)
        }
    }
}