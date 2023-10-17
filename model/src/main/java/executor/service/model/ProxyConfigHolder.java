package executor.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProxyConfigHolder {

    private ProxyNetworkConfig proxyNetworkConfig;

    private ProxyCredentials proxyCredentials;

}