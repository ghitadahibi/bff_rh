package ma.pca.bff.gateway;

import ma.pca.bff.gateway.dto.DailyGatewayRequest;
import ma.pca.bff.gateway.dto.DailyGatewayResponse;
import ma.pca.bff.gateway.dto.SessionGatewayRequest;
import ma.pca.bff.gateway.dto.SessionGatewayResponse;
import ma.pca.starter.web.gateway.Gateway;
import ma.pca.starter.web.gateway.GatewayService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "gateway", url = "${gateway.url}")
@Gateway
public interface ExampleGateway {
    @PostMapping(path = "/${gateway.path}")
    @GatewayService(serviceId = "daily")
    DailyGatewayResponse process(DailyGatewayRequest request);

    @PostMapping(path = "/session")
    SessionGatewayResponse process(SessionGatewayRequest request);
}
