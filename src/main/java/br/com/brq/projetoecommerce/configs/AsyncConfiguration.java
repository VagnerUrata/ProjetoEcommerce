package br.com.brq.projetoecommerce.configs;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
@EnableAsync
public class AsyncConfiguration {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${application.async.corePoolSize}")
    private int corePoolSize;

    @Value("${application.async.maxPoolSize}")
    private int maxPoolSize;

    @Value("${application.async.queueCapacity}")
    private int queueCapacity;

    @Bean
    public Executor taskExecutor() {
        final var taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(corePoolSize);
        taskExecutor.setMaxPoolSize(maxPoolSize);
        taskExecutor.setQueueCapacity(queueCapacity);
        taskExecutor.setThreadNamePrefix(applicationName + "Thread-");
        taskExecutor.initialize();
        return taskExecutor;
    }
}