package executor.service.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RemoteConnectionDto {
    @Value("${publisher.scenario.url}")
    private String scenarioUrl;
    @Value("${publisher.proxy.url}")
    private String proxyUrl;
    @Value("${publisher.jwt.token}")
    private String token;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RemoteConnectionDto remoteConnectionDto)) return false;
        return Objects.equals(scenarioUrl, remoteConnectionDto.scenarioUrl) &&
                Objects.equals(proxyUrl, remoteConnectionDto.proxyUrl) &&
                Objects.equals(token, remoteConnectionDto.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scenarioUrl, proxyUrl, token);
    }

    public String getProxyUrl() {
        return proxyUrl;
    }

    public void setProxyUrl(String proxyUrl) {
        this.proxyUrl = proxyUrl;
    }

    public String getScenarioUrl() {
        return scenarioUrl;
    }

    public void setScenarioUrl(String scenarioUrl) {
        this.scenarioUrl = scenarioUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
