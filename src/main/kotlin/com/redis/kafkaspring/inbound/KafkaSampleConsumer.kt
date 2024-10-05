package com.redis.kafkaspring.inbound

import TestProto
import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component
import org.springframework.util.backoff.FixedBackOff


private val log = KotlinLogging.logger {}

@Component
class KafkaSampleConsumer {
    @KafkaListener(
        topics = ["error-test"],
        containerFactory = "transactionKafkaListenerContainerFactory",
    )
    fun consume(record: ConsumerRecord<String, TestProto>, ack: Acknowledgment) {
        log.info {"group1 consumer 1"}
        val testProto = record.value()


        log.info { "Consumed: $testProto" }
        log.info { "Consumed: ${testProto.email}" }
        log.info { "Consumed: ${testProto.password}" }

        throw RuntimeException("error")
    }

    @KafkaListener(
        topics = ["word-count"],
        containerFactory = "transactionKafkaListenerContainerFactory",
    )
    fun consume2(record: ConsumerRecord<String, TestProto>, ack: Acknowledgment) {

    }
}

// 1. 질문
// 2. 데드레터 큐 에러핸들링로직 구현해보기
// 3. 카프카 데비지움 커넥트 해보기