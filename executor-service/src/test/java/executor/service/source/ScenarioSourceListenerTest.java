package executor.service.source;

import executor.service.model.RemoteConnectionDto;
import executor.service.model.ScenarioDto;
import executor.service.queue.scenario.ScenarioSourceQueueHandler;
import executor.service.source.listener.SourceListener;
import executor.service.source.listener.ScenarioSourceListener;
import executor.service.source.okhttp.OkhttpLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class ScenarioSourceListenerTest {
    private OkhttpLoader loader;
    private SourceListener sourceListener;
    private ScenarioSourceQueueHandler scenarios;

    @BeforeEach
    void setUp() {
        loader = mock(OkhttpLoader.class);
        scenarios = mock(ScenarioSourceQueueHandler.class);
        RemoteConnectionDto dto = new RemoteConnectionDto("http://some/scenario/url", "http://some/proxy/url", "token");
        sourceListener = new ScenarioSourceListener(loader, dto, scenarios);
    }

    @Test
    void testFetchData() {
        when(loader.loadData(any(), eq(ScenarioDto.class))).thenReturn(List.of());
        sourceListener.fetchData();
        verify(loader, times(1)).loadData(any(), eq(ScenarioDto.class));
    }

    @Test
    void testGetOne_shouldSaveScenarios() {
        ScenarioDto expected = new ScenarioDto();
        when(loader.loadData(any(), eq(ScenarioDto.class))).thenReturn(List.of(expected));
        sourceListener.fetchData();
        verify(scenarios, times(1)).addAll(eq(List.of(expected)));
    }

    @Test
    void testGetOne_shouldNotSaveScenarios() {
        when(loader.loadData(any(), eq(ScenarioDto.class))).thenReturn(List.of());
        sourceListener.fetchData();
        verify(scenarios, times(1)).addAll(List.of());
    }
}