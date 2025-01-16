package com.raynor.geek.vectordb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class VectordbApplication

fun main(args: Array<String>) {
    runApplication<VectordbApplication>(*args)
}
