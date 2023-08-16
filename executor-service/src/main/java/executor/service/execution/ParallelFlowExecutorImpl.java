package executor.service.execution;

import executor.service.factory.webdriverinitializer.WebDriverProvider;

import executor.service.execution.scenario.ScenarioExecutor;

import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.ThreadPoolConfigDto;
import executor.service.queue.proxy.ProxySourceQueueHandler;
import executor.service.queue.scenario.ScenarioSourceQueueHandler;
import executor.service.source.listener.SourceListener;
import org.openqa.selenium.WebDriver;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Service
public class ParallelFlowExecutorImpl implements ParallelFlowExecutor {
    private final ExecutionService executionService;
    private final ThreadPoolConfigDto threadPoolConfigDto;
    private final List<SourceListener> listeners;
    private final ScenarioExecutor scenarioExecutor;
    private final WebDriverProvider driverProvider;
    private final ProxySourceQueueHandler proxies;
    private final ScenarioSourceQueueHandler scenarios;

    public ParallelFlowExecutorImpl(ExecutionService executionService, List<SourceListener> listeners, WebDriverProvider driverProvider,
                                    ThreadPoolConfigDto threadPoolConfigDto, ScenarioExecutor scenarioExecutor,
                                    ProxySourceQueueHandler proxies, ScenarioSourceQueueHandler scenarios) {
        this.listeners = listeners;
        this.threadPoolConfigDto = threadPoolConfigDto;
        this.executionService = executionService;
        this.scenarioExecutor = scenarioExecutor;
        this.driverProvider = driverProvider;
        this.proxies = proxies;
        this.scenarios = scenarios;
    }

    @Scheduled(fixedRate = 120000)
    @Override
    public void runInParallelFlow() {
        listeners.forEach(SourceListener::fetchData);
        if(scenarios.getSize() != 0) executeScenarios();
    }

    private void executeScenarios() {
        Optional<ProxyConfigHolderDto> proxy = proxies.poll();
        Supplier<WebDriver> createWebDriver = () -> proxy.map(driverProvider::create).orElseGet(driverProvider::create);
        ThreadPoolExecutor fixedThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadPoolConfigDto.getCorePoolSize());
        fixedThreadPool.setKeepAliveTime(threadPoolConfigDto.getKeepAliveTime(), TimeUnit.MILLISECONDS);
        for (int i = 0; i < threadPoolConfigDto.getCorePoolSize(); i++) {
            fixedThreadPool.execute(() -> executionService.execute(createWebDriver.get(), scenarios, scenarioExecutor));
        }
        fixedThreadPool.shutdown();
    }

}