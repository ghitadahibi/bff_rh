package ma.pca.bff.config;

import ma.pca.bff.config.props.ExampleProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExampleConfiguration {

    @Autowired
    private ExampleProperties exampleProperties;

}
