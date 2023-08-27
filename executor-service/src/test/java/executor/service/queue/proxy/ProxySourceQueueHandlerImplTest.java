package executor.service.queue.proxy;


import executor.service.model.ProxyConfigHolder;
import executor.service.queue.QueueHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class ProxySourceQueueHandlerImplTest {
    private QueueHandler<ProxyConfigHolder> basicHandler;
    private ProxySourceQueueHandler queueHandler;

    @BeforeEach
    public void setUp() {
        basicHandler = mock(QueueHandler.class);
        queueHandler = new ProxySourceQueueHandlerImpl(basicHandler);
    }

    @Test
    void testAdd() {
        ProxyConfigHolder proxy = new ProxyConfigHolder();
        queueHandler.add(proxy);
        verify(basicHandler, times(1)).add(eq(proxy));
    }

    @Test
    void testAddAll() {
        List<ProxyConfigHolder> proxies = List.of(new ProxyConfigHolder(), new ProxyConfigHolder());
        queueHandler.addAll(proxies);
        verify(basicHandler, times(1)).addAll(eq(proxies));
    }

    @Test
    void testPoll() {
        queueHandler.poll();
        verify(basicHandler, times(1)).poll();
    }

    @Test
    void testRemoveAll() {
        queueHandler.removeAll();
        verify(basicHandler, times(1)).removeAll();
    }

    @Test
    void testGetSize() {
        queueHandler.getSize();
        verify(basicHandler, times(1)).getSize();
    }
}