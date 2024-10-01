package com.redis.kafkaspring

data class QueueMessage(
    val topic : String = "partition-over",
    val partitions: Int,
    val key : String,
    val message: String,
)
