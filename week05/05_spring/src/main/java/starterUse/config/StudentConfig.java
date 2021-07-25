package starterUse.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import starterUse.properties.StudentPropertise;

@Configuration
@EnableConfigurationProperties(StudentPropertise.class)
@ConditionalOnProperty(
        prefix = "student",
        name = "isopen",
        havingValue = "true"
)
public class StudentConfig {

}
