package executor.service.source.listener;

import executor.service.source.okhttp.AuthorizationType;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.RemoteConnection;
import executor.service.queue.proxy.ProxySourceQueueHandler;
import executor.service.source.okhttp.OkhttpLoader;
import okhttp3.Request;
import org.springframework.stereotype.Component;


import static com.google.common.net.HttpHeaders.AUTHORIZATION;
@Component
public class LazyProxySourceListener implements SourceListener {
    private final OkhttpLoader loader;
    private final Request request;
    private final ProxySourceQueueHandler proxies;

    public LazyProxySourceListener(OkhttpLoader loader, RemoteConnection remoteConnection, ProxySourceQueueHandler proxies) {
        this.loader = loader;
        this.proxies = proxies;
        request = new Request.Builder().url(remoteConnection.getProxyUrl())
                .delete().header(AUTHORIZATION, AuthorizationType.BEARER.getPrefix() + remoteConnection.getToken()).build();
    }

    @Override
    public void fetchData() {
        if(proxies.getSize() == 0) proxies.addAll(loader.loadData(request, ProxyConfigHolder.class));
    }
}
