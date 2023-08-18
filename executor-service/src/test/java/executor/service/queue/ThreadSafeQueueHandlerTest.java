package executor.service.queue;

import executor.service.model.ScenarioDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class ThreadSafeQueueHandlerTest {
    private ThreadSafeQueueHandler<ScenarioDto> handler;
    private static final int THREAD_COUNT = 8;
    private static final int ELEMENT_COUNT = 200;
    private CountDownLatch countDownLatch;
    private ExecutorService executorService;

    @BeforeEach
    public void setUp() {
        handler = new ThreadSafeQueueHandler<>();
        countDownLatch = new CountDownLatch(THREAD_COUNT);
        executorService = Executors.newFixedThreadPool(THREAD_COUNT);
    }

    @Test
    public void addTest() throws InterruptedException {
        Runnable addRunnableTask = () -> {
            handler.add(new ScenarioDto());
            countDownLatch.countDown();
        };
        for (int i = 0; i < THREAD_COUNT; i++) executorService.submit(addRunnableTask);
        countDownLatch.await();
        assertEquals(THREAD_COUNT, handler.removeAll().size());
    }

    @Test
    public void addAllTest() throws InterruptedException {
        List<ScenarioDto> elements = IntStream.range(0, ELEMENT_COUNT).boxed().map(v -> new ScenarioDto()).toList();
        Runnable addAllRunnableTask = () -> {
            handler.addAll(elements);
            countDownLatch.countDown();
        };
        for (int i = 0; i < THREAD_COUNT; i++) executorService.submit(addAllRunnableTask);
        countDownLatch.await();
        assertEquals(THREAD_COUNT * ELEMENT_COUNT, handler.removeAll().size());
    }

    @Test
    public void pollTest() throws InterruptedException {
        IntStream.range(0, ELEMENT_COUNT).forEach
                (i -> handler.add(new ScenarioDto()));
        Runnable pollRunnableTask = () -> {
            handler.poll();
            countDownLatch.countDown();
        };
        for (int i = 0; i < THREAD_COUNT; i++) executorService.submit(pollRunnableTask);
        countDownLatch.await();
        assertEquals(ELEMENT_COUNT - THREAD_COUNT, handler.removeAll().size());
    }

    @Test
    public void removeAllTest() throws InterruptedException {
        IntStream.range(0, ELEMENT_COUNT).forEach
                (i -> handler.add(new ScenarioDto()));
        AtomicInteger resultSize = new AtomicInteger(0);
        Runnable removeAllRunnableTask = () -> {
            resultSize.addAndGet(handler.removeAll().size());
            countDownLatch.countDown();
        };
        for (int i = 0; i < THREAD_COUNT; i++) executorService.submit(removeAllRunnableTask);
        countDownLatch.await();
        assertEquals(ELEMENT_COUNT, resultSize.get());
        assertEquals(0, handler.removeAll().size());
    }
}