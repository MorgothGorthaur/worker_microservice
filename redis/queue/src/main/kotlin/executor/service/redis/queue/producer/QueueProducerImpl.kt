package executor.service.redis.queue.producer

import executor.service.aop.logger.annotation.Logged
import executor.service.model.Scenario
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
@Logged
class QueueProducerImpl(private val template: RedisTemplate<String, Any>) : QueueProducer {
    private val key = "report.queue.key"
    override fun add(scenario: Scenario) { template.opsForList().leftPush(key, scenario) }
}