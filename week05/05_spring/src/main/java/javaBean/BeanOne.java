package javaBean;

import lombok.Data;


/**
 * 在applicationContext.xml 中声明
 */
@Data
public class BeanOne {
    private String name;

    public String toString() {
        return name;
    }
}
