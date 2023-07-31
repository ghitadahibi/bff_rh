package ma.pca.bff.service;

import ma.pca.bff.dto.ExamplePageableResponse;
import ma.pca.bff.dto.ExampleRequest;
import ma.pca.bff.dto.ExampleResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ExampleService {
    ExampleResponse processRequest(ExampleRequest request);


    ExamplePageableResponse examplePage(final int page);



}
