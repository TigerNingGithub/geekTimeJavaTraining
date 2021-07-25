package javaBean.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Component 声明bean
 */
@Data
//@Component
public class BeanThree {

    private String name;

    public String toString() {
        return name;
    }
}
