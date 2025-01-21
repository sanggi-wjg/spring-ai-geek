//package com.raynor.geek.llmservice.aspect
//
//import org.aspectj.lang.ProceedingJoinPoint
//import org.aspectj.lang.annotation.Around
//import org.aspectj.lang.annotation.Aspect
//import org.slf4j.LoggerFactory
//import org.springframework.ai.ollama.api.OllamaApi.ChatRequest
//import org.springframework.stereotype.Component
//
//@Aspect
//@Component
//class DemoAspect {
//    private val logger = LoggerFactory.getLogger(this::class.java)
//
//    @Around("execution(* org.springframework.ai.ollama.api.OllamaApi.chat*(..)) || execution(* org.springframework.ai.ollama.api.OllamaApi.streamChat*(..))")
//    fun demoAroundAdvice(joinPoint: ProceedingJoinPoint): Any? {
//        val chatRequest = joinPoint.args[0] as ChatRequest
//
//        return joinPoint.proceed()
//    }
//}