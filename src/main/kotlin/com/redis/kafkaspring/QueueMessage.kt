package com.redis.kafkaspring

data class QueueMessage(
    val clientId: String,
    val subjectId: String,
    val message: String,
)
