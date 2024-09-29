package com.redis.kafkaspring

import com.redis.kafkaspring.outbound.KafkaSampleProducer
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class KafkaSampleController(
    private val kafkaSampleProducer: KafkaSampleProducer,
) {
    @PostMapping("/kafka")
    fun kafka(@RequestBody message: QueueMessage) {
        kafkaSampleProducer.publish(message)
    }
}