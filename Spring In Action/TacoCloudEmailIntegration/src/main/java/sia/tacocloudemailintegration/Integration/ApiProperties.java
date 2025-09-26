package sia.tacocloudemailintegration.Integration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix="tacocloud.api")
@Component
public class ApiProperties {
    private String url;
}