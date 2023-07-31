package ma.pca.bff.service.impl;

import lombok.SneakyThrows;
import ma.pca.bff.config.ExampleExceptionType;
import ma.pca.bff.config.props.ExampleProperties;
import ma.pca.bff.dto.ExampleDto;
import ma.pca.bff.dto.ExamplePageableResponse;
import ma.pca.bff.dto.ExampleRequest;
import ma.pca.bff.dto.ExampleResponse;
import ma.pca.bff.gateway.ExampleGateway;
import ma.pca.bff.gateway.dto.DailyGatewayRequest;
import ma.pca.bff.gateway.dto.DailyGatewayResponse;
import ma.pca.bff.mapper.ExampleMapper;
import ma.pca.bff.model.entities.Example;
import ma.pca.bff.repository.ExampleRepository;
import ma.pca.bff.service.ExampleService;
import ma.pca.starter.audit.annotation.Auditable;

import ma.pca.starter.web.exception.FunctionalRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExampleServiceImpl implements ExampleService {

    @Autowired
    ExampleRepository exampleRepository;

    @Autowired
    ExampleProperties exampleProperties;

    @Autowired
    ExampleGateway exampleGateway;

    @Override
    @Auditable
    public ExampleResponse processRequest(final ExampleRequest request) {
        Example ex = ExampleMapper.INSTANCE.mapExampleRequestToExample(request);
        ex = exampleRepository.findById(request.getKey()).orElseThrow(
            () -> new FunctionalRuntimeException(
                ExampleExceptionType.EXAMPLE_EXCEPTION,
                String.format("No Example found for Id %s", request.getKey())));
        final DailyGatewayResponse response = exampleGateway.process(
            DailyGatewayRequest.builder().key(request.getKey()).build());
        return ExampleResponse.builder().value(response.getValue()).build();
    }





    @Override
    public ExamplePageableResponse examplePage(final int page) {
        final List<ExampleDto> exampleResponseList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            exampleResponseList
                .add(ExampleDto.builder().value(String.valueOf(i)).build());
        }
        return ExamplePageableResponse.builder().data(exampleResponseList)
            .build().paginate(page, 150);
    }




}
