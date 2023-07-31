package ma.pca.bff.controller;

import lombok.extern.slf4j.Slf4j;
import ma.pca.bff.config.ExampleExceptionType;
import ma.pca.bff.config.Resources;
import ma.pca.bff.dto.ExamplePageableResponse;
import ma.pca.bff.dto.ExampleRequest;
import ma.pca.bff.dto.ExampleResponse;
import ma.pca.bff.service.ExampleService;
import ma.pca.starter.web.exception.FunctionalRuntimeException;
import ma.pca.starter.web.rest.RequestDetails;
import ma.pca.starter.web.rest.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.core.io.ByteArrayResource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
@RestController
@RequestMapping(Resources.API + Resources.EXAMPLE)
@Slf4j
public class ExampleController {
    @Autowired
    ExampleService exampleService;



    @Autowired
    RestClient restClient;

    @GetMapping
    public ExampleResponse getExample() {
        restClient.execute(
            RequestDetails.builder().path("http://localhost:8081/api/exception")
                .build(), HttpMethod.GET, null, ExampleResponse.class, null);
        throw new FunctionalRuntimeException(
            ExampleExceptionType.EXAMPLE_EXCEPTION, "Testingé Exception");
    }

    @GetMapping(path = "/page")
    public ExamplePageableResponse getExamplePage(
        @RequestParam(required = false)
        final Integer page) {
        return exampleService.examplePage(page == null ? 1 : page);
    }

    @PostMapping(path = "/gateway")
    public ExampleResponse gatewayExample(
        @RequestBody
        final ExampleRequest request) {
        return exampleService.processRequest(request);
    }

    @PostMapping
    public ExampleResponse postExample(
        @RequestBody
        final ExampleRequest request) {
        return ExampleResponse.builder().value("Testing").build();
    }
    @GetMapping("/jobmatching")
    @ResponseBody
    public String getJobMatching() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:8000/jobmatching"; // Replace with the URL of your FastAPI API
        String response = restTemplate.getForObject(apiUrl, String.class);

        System.out.println(response);
        return response;
    }
}
