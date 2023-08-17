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
    @PostMapping("/uploadjoboffre")
    public ResponseEntity<String> uploadJobOffer(@RequestParam("joboffre_nom") String joboffer_nom,
                                                 @RequestParam("joboffre") MultipartFile joboffer) throws IOException {

        // Check if the file is empty or missing
        if (joboffer == null || joboffer.isEmpty()) {
            throw new IllegalArgumentException("Job offer file is empty or missing");
        }

        // Set the API endpoint URL
        String apiUrl = "http://localhost:8000/uploadjoboffre";

        // Create a RestTemplate object
        RestTemplate restTemplate = new RestTemplate();

        // Set the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Set the request body
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("joboffre_nom", joboffer_nom);
        requestBody.add("joboffre", new ByteArrayResource(joboffer.getBytes()) {
            @Override
            public String getFilename() {
                return joboffer.getOriginalFilename();
            }
        });
        System.out.println(joboffer_nom);
        // Create the request entity
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Send the POST request to the FastAPI backend
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

        // Return the response
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }


    @GetMapping("/jobmatching")
    @ResponseBody
    public String getJobMatching(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:8000/jobmatching?page=" + page + "&size=" + size;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
        System.out.println(response.getBody());
        return response.getBody();
    }


    @GetMapping("/joboffer")
    @ResponseBody
    public String getJoboffer( @RequestParam(defaultValue = "0") Integer page,
                               @RequestParam(defaultValue = "5") Integer size) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:8000/joboffer?page="+ page + "&size=" + size; // Replace with the URL of your FastAPI API
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
        System.out.println(response.getBody());
        return response.getBody();
    }

    @DeleteMapping("/joboffers")
    @ResponseBody
    public String deleteAllJoboffers() {
        RestTemplate restTemplate = new RestTemplate();
        String deleteAllJoboffersUrl = "http://localhost:8000/deleteall";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(deleteAllJoboffersUrl, HttpMethod.DELETE, null, String.class);
        return response.getBody();
    }
    @DeleteMapping("/joboffers/{jobOfferName}")
    @ResponseBody
    public String deleteJobOfferById(@PathVariable String jobOfferName) {
        RestTemplate restTemplate = new RestTemplate();
        String deleteJobOfferUrl = "http://localhost:8000/joboffers/" + jobOfferName;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(deleteJobOfferUrl, HttpMethod.DELETE, null, String.class);
        return response.getBody();
    }
}
