package ma.pca.bff.gateway.processor;

import lombok.extern.slf4j.Slf4j;
import ma.pca.bff.gateway.dto.DailyGatewayRequest;
import ma.pca.bff.gateway.dto.DailyGatewayResponse;
import ma.pca.starter.web.gateway.GatewayServiceProcessor;
import org.springframework.stereotype.Component;

@Component("daily")
@Slf4j
public class DailyServiceProcessor implements
    GatewayServiceProcessor<DailyGatewayResponse, DailyGatewayRequest> {

    @Override
    public DailyGatewayRequest preProcess(final Object[] request) {
        log.info("preProcess {}", request);
        return (DailyGatewayRequest) request[0];
    }

    @Override
    public DailyGatewayResponse postProcess(DailyGatewayResponse dailyGatewayResponse, Object[] objects) {
        return null;
    }


}
