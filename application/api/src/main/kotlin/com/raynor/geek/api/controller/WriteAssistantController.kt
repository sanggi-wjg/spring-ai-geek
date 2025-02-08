package com.raynor.geek.api.controller

import com.raynor.geek.api.controller.dto.request.WritingRequestDto
import com.raynor.geek.llmservice.service.WritingService
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/write-assistant")
class WriteAssistantController(
    private val writingService: WritingService,
) {

    @PostMapping("")
    fun helpWriting(
        @Valid @RequestBody writingRequestDto: WritingRequestDto,
    ): ResponseEntity<String> {
        return writingService.helpWriting(writingRequestDto.text).let {
            ResponseEntity.ok(it.results.first().output.text)
        }
    }

    @PostMapping("/stream", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun helpWritingStream(
        @Valid @RequestBody writingRequestDto: WritingRequestDto,
    ): Flux<String> {
        return writingService.helpWriting(writingRequestDto.text).let {
            Flux.just(it.results.first().output.text)
        }
    }
}
