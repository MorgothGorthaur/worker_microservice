package executor.service.source;

import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.RemoteConnectionDto;
import executor.service.queue.proxy.ProxySourceQueueHandler;
import executor.service.source.listener.SourceListener;
import executor.service.source.listener.LazyProxySourceListener;
import executor.service.source.okhttp.OkhttpLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class LazyProxySourceListenerTest {

    private OkhttpLoader loader;
    private SourceListener sourceListener;
    private ProxySourceQueueHandler proxies;

    @BeforeEach
    void setUp() {
        loader = mock(OkhttpLoader.class);
        proxies = mock(ProxySourceQueueHandler.class);
        RemoteConnectionDto dto = new RemoteConnectionDto("http://some/scenario/url", "http://some/proxy/url", "token");
        sourceListener = new LazyProxySourceListener(loader, dto, proxies);
    }

    @Test
    void testFetchData() {
        when(loader.loadData(any(), eq(ProxyConfigHolderDto.class))).thenReturn(List.of());
        sourceListener.fetchData();
        verify(loader, times(1)).loadData(any(), eq(ProxyConfigHolderDto.class));
    }

    @Test
    void testGetOne_shouldSaveProxies() {
        ProxyConfigHolderDto expected = new ProxyConfigHolderDto();
        when(proxies.getSize()).thenReturn(0);
        when(loader.loadData(any(), eq(ProxyConfigHolderDto.class))).thenReturn(List.of(expected));
        sourceListener.fetchData();
        verify(proxies, times(1)).addAll(eq(List.of(expected)));
    }

    @Test
    void testGetOne_shouldNotSaveProxies() {
        when(proxies.getSize()).thenReturn(1);
        sourceListener.fetchData();
        verify(proxies, times(0)).addAll(any());
    }
}