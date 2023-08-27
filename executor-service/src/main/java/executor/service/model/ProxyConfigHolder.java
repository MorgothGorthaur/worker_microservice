package executor.service.model;

import java.util.Objects;

public class ProxyConfigHolder {

    private ProxyNetworkConfig proxyNetworkConfig;

    private ProxyCredentials proxyCredentials;


    public ProxyConfigHolder() { }

    public ProxyConfigHolder(ProxyNetworkConfig proxyNetworkConfig, ProxyCredentials proxyCredentials) {
        this.proxyNetworkConfig = proxyNetworkConfig;
        this.proxyCredentials = proxyCredentials;
    }

    public ProxyNetworkConfig getProxyNetworkConfig() {
        return proxyNetworkConfig;
    }

    public void setProxyNetworkConfig(ProxyNetworkConfig proxyNetworkConfig) {
        this.proxyNetworkConfig = proxyNetworkConfig;
    }

    public ProxyCredentials getProxyCredentials() {
        return proxyCredentials;
    }

    public void setProxyCredentials(ProxyCredentials proxyCredentials) {
        this.proxyCredentials = proxyCredentials;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProxyConfigHolder that = (ProxyConfigHolder) o;

        if (!Objects.equals(proxyNetworkConfig, that.proxyNetworkConfig)) return false;
        return Objects.equals(proxyCredentials, that.proxyCredentials);
    }

    @Override
    public int hashCode() {
        int result = proxyNetworkConfig.hashCode();
        result = 31 * result + proxyCredentials.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ProxyConfigHolderDto{" +
                "proxyNetworkConfig=" + proxyNetworkConfig +
                ", proxyCredentials=" + proxyCredentials +
                '}';
    }
}