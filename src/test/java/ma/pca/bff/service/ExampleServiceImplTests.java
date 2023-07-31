package ma.pca.bff.service;

import ma.pca.bff.dto.ExampleRequest;
import ma.pca.bff.dto.ExampleResponse;
import ma.pca.bff.gateway.ExampleGateway;
import ma.pca.bff.gateway.dto.DailyGatewayRequest;
import ma.pca.bff.gateway.dto.DailyGatewayResponse;
import ma.pca.bff.model.entities.Example;
import ma.pca.bff.repository.ExampleRepository;
import ma.pca.bff.service.impl.ExampleServiceImpl;
import ma.pca.starter.test.annotation.Case;
import ma.pca.starter.test.annotation.DataParam;
import ma.pca.starter.test.annotation.Scenario;
import ma.pca.starter.test.annotation.UnitTest;
import ma.pca.starter.test.assertion.ResultAssertion;
import ma.pca.starter.test.runner.TestCase;
import ma.pca.starter.test.runner.TestRunner;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@UnitTest
@Scenario("scenario-001")
public class ExampleServiceImplTests {

    @InjectMocks
    ExampleServiceImpl exampleService;

    @Mock
    ExampleRepository exampleRepository;

    @Mock
    ExampleGateway exampleGateway;

    @Test
    @Case("case-001")
    void test1(final TestRunner runner,
        @DataParam("request") final ExampleRequest request,
        @DataParam("example") final Example example,
        @DataParam("response") final ExampleResponse response,
        @DataParam("request") final DailyGatewayRequest gatewayRequest,
        @DataParam("response") final DailyGatewayResponse gatewayResponse) {
        final TestCase C_001 = TestCase.builder().mock(exampleRepository, exampleRepository -> {
            when(exampleRepository.findById(request.getKey())).thenReturn(
                Optional.of(example));
        }).mock(exampleGateway, exampleGateway -> {
            when(exampleGateway.process(gatewayRequest)).thenReturn(gatewayResponse);
        }).assertFor(ResultAssertion.builder()
            .source(() -> exampleService.processRequest(request).getValue()).matcher(equalTo(response.getValue()))
            .build()).build();

        runner.test(C_001);
    }
}
