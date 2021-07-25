package starterUse.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import starterUse.properties.KlassProperties;

@Configuration
@EnableConfigurationProperties(KlassProperties.class)
public class KlassConfig {
}
