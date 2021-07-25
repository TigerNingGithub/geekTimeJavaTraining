package jdbcUse;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
public class JDBC_Config {
    private String Url="jdbc:mysql://127.0.0.1:3306/mysql_test?useUnicode=true&characterEncoding=UTF-8";
    private String DriverName="com.mysql.jdbc.Driver";
    private String UserName="root";
    private String Password="root";
}
