package javaBean.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Data
public class BeanThreeSetValue {
    @Bean("BeanThree")
    public BeanThree getBean() {
        BeanThree beanThree = new BeanThree();
        beanThree.setName("hello BeanThree");
        return beanThree;
    }

    @Autowired
    @Qualifier("BeanThree")
    public void setBeanTwo3(BeanThree beanTwo) {
        BeanThreeSetValue.beanThree = beanTwo;
    }
    public static BeanThree beanThree;
}
