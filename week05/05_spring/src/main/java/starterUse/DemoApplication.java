package starterUse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import starterUse.properties.StudentPropertise;

/**
 * 还不懂怎么对自动配置类进行测试...
 */
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private StudentPropertise studentPropertise;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(studentPropertise);
    }
}
