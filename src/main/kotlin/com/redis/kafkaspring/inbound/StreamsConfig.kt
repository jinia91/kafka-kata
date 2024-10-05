package com.redis.kafkaspring.inbound

import TestProto
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG
import io.confluent.kafka.streams.serdes.protobuf.KafkaProtobufSerde
import java.time.Duration
import java.util.*
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.TimeWindows
import org.apache.kafka.streams.kstream.ValueMapper
import org.apache.kafka.streams.kstream.internals.TimeWindow
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafkaStreams
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration
import org.springframework.kafka.config.KafkaStreamsConfiguration
import org.springframework.kafka.config.StreamsBuilderFactoryBean
import org.springframework.kafka.config.StreamsBuilderFactoryBeanConfigurer
import kotlin.collections.HashMap


@Configuration
@EnableKafkaStreams
class KafkaStreamsConfig {
    @Bean(name = [KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME])
    fun kStreamsConfigs(): KafkaStreamsConfiguration {
        val props: MutableMap<String, Any> = HashMap()
        props[StreamsConfig.APPLICATION_ID_CONFIG] = "testStreams"
        props[StreamsConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:10001, localhost:10002, localhost:10000"
        props[StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG] = Serdes.String()::class.java
        props["schema.registry.url"] = "http://localhost:8081"
        props[StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG] = testProtobufSerde()::class.java
        props["specific.protobuf.value.type"] = TestProto::class.java
        return KafkaStreamsConfiguration(props)
    }

    private fun testProtobufSerde(): KafkaProtobufSerde<TestProto> {
        val protobufSerde: KafkaProtobufSerde<TestProto> = KafkaProtobufSerde<TestProto>()
        val serdeConfig: MutableMap<String, String> = HashMap()
        serdeConfig[SCHEMA_REGISTRY_URL_CONFIG] = "http://localhost:8081"
        protobufSerde.configure(serdeConfig, false)
        return protobufSerde
    }

    @Bean
    fun configurer(): StreamsBuilderFactoryBeanConfigurer {
        return StreamsBuilderFactoryBeanConfigurer { fb: StreamsBuilderFactoryBean ->
            fb.setStateListener { newState: KafkaStreams.State, oldState: KafkaStreams.State ->
                println("State transition from $oldState to $newState")
            }
        }
    }

    @Bean
    fun kStream(kStreamBuilder: StreamsBuilder): KStream<String, TestProto> {
        val stream = kStreamBuilder.stream<String, TestProto>("streamingTopic1")
        stream
            .mapValues<TestProto>(ValueMapper<TestProto, TestProto> { value: TestProto ->
                value.toBuilder().setEmail("*****").build()
            })
            .to("streamingTopic2")
        return stream
    }
}