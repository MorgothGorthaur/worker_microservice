package executor.service.redis.configuration.model

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@PropertySource("classpath:redis.properties")
data class RedisConfigHolder(
    @Value("\${spring.data.redis.host}")
    var redisHost: String,

    @Value("\${spring.data.redis.port}")
    var redisPort: Int,
)
