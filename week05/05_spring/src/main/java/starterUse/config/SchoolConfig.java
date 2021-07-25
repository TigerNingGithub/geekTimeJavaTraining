package starterUse.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import starterUse.properties.SchoolProperties;

@Configuration
@EnableConfigurationProperties(SchoolProperties.class)
public class SchoolConfig {

}
