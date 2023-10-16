package executor.service.queue.proxy;



import executor.service.model.ProxyConfigHolder;
import executor.service.queue.QueueHandler;
import lombok.RequiredArgsConstructor;

import java.util.*;
@RequiredArgsConstructor
public class ProxySourceQueueHandlerImpl implements ProxySourceQueueHandler {
    private final QueueHandler<ProxyConfigHolder> handler;

    @Override
    public void add(ProxyConfigHolder element) {
        handler.add(element);
    }

    @Override
    public void addAll(List<ProxyConfigHolder> elements) {
        handler.addAll(elements);
    }

    @Override
    public Optional<ProxyConfigHolder> poll() {
        return handler.poll();
    }

    @Override
    public List<ProxyConfigHolder> removeAll() {
        return handler.removeAll();
    }

    @Override
    public int getSize() {
        return handler.getSize();
    }
}
