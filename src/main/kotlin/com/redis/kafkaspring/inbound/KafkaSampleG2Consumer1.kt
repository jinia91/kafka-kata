//package com.redis.kafkaspring.inbound
//
//import TestProto
//import io.github.oshai.kotlinlogging.KotlinLogging
//import org.apache.kafka.clients.consumer.ConsumerRecord
//import org.springframework.kafka.annotation.KafkaListener
//import org.springframework.stereotype.Component
//
//private val log = KotlinLogging.logger {}
//
//@Component
//class KafkaSampleG2Consumer1 {
//    @KafkaListener(
//        topics = ["partition-over"],
//        containerFactory = "transactionKafkaListenerContainerFactory2",
//    )
//    fun consume(record: ConsumerRecord<String, TestProto>) {
//        log.info {"group2 consumer 1"}
//        val testProto = record.value()
//
//
//        log.info { "Consumed: $testProto" }
//        log.info { "Consumed: ${testProto.email}" }
//        log.info { "Consumed: ${testProto.password}" }
//    }
//}