package com.redis.kafkaspring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaspringApplication

fun main(args: Array<String>) {
    runApplication<KafkaspringApplication>(*args)
}
