package ma.pca.bff.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("example")
@Data
@Component
public class ExampleProperties {
    private String key;
}
