package executor.service.source.listener;

import executor.service.source.config.Header;
import executor.service.source.okhttp.AuthorizationType;
import executor.service.source.model.RemoteConnection;
import executor.service.model.Scenario;
import executor.service.collection.queue.scenario.ScenarioSourceQueueHandler;
import executor.service.source.okhttp.loader.OkhttpLoader;
import okhttp3.Request;
import org.springframework.stereotype.Component;


@Component
public class ScenarioSourceListener implements SourceListener {

    private final OkhttpLoader loader;
    private final ScenarioSourceQueueHandler scenarios;
    private final Request request;

    public ScenarioSourceListener(OkhttpLoader loader, RemoteConnection remoteConnection, ScenarioSourceQueueHandler scenarios) {
        this.loader = loader;
        this.scenarios = scenarios;
        request = new Request.Builder().url(remoteConnection.getScenarioUrl())
                .delete().addHeader(Header.AUTHORIZATION.getValue(), AuthorizationType.BEARER.getPrefix() + remoteConnection.getToken()).build();
    }

    @Override
    public void fetchData() {
        scenarios.addAll(loader.loadData(request, Scenario.class));
    }

}
