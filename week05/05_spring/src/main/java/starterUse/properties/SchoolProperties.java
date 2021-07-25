package starterUse.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "school")
public class SchoolProperties {
    KlassProperties class1;
    StudentPropertise student100;
}
