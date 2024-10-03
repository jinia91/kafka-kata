//package com.redis.kafkaspring.inbound
//
//import TestProto
//import com.redis.kafkaspring.QueueMessage
//import io.github.oshai.kotlinlogging.KotlinLogging
//import org.apache.kafka.clients.consumer.ConsumerRecord
//import org.springframework.kafka.annotation.KafkaListener
//import org.springframework.kafka.listener.MessageListener
//import org.springframework.kafka.support.Acknowledgment
//import org.springframework.stereotype.Component
//
//private val log = KotlinLogging.logger {}
//
//@Component
//class KafkaSampleConsumer2 {
//    @KafkaListener(
//        topics = ["commit-test"],
//        containerFactory = "transactionKafkaListenerContainerFactory",
//    )
//    fun consume(record: ConsumerRecord<String, TestProto>, ack: Acknowledgment) {
//        log.info {"group1 consumer 2"}
//        val testProto = record.value()
//
//
//        log.info { "Consumed: $testProto" }
//        log.info { "Consumed: ${testProto.email}" }
//        log.info { "Consumed: ${testProto.password}" }
//            ack.acknowledge()
//    }
//}