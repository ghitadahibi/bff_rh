package ma.pca.bff.controller;

import ma.pca.starter.test.annotation.Case;
import ma.pca.starter.test.annotation.DataParam;
import ma.pca.starter.test.annotation.IntegrationTest;
import ma.pca.starter.test.annotation.Scenario;
import ma.pca.starter.test.runner.TestCase;
import ma.pca.starter.test.runner.TestRunner;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@Scenario("scenario-001")
public class ExampleControllerTests {

    @Case("case-001")
    void callController(final TestRunner runner,
        @DataParam("request")
        final String request,
        @DataParam("response")
        final String response) {
        final TestCase C_001 = TestCase.builder()
            .call(post("/api/example").contentType(MediaType.APPLICATION_JSON).content(request).with(jwt()), status().isOk(), content().json(response))
            .build();

        runner.test(C_001);
    }
}
