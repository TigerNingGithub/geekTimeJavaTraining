package javaBean;


import javaBean.bean.BeanThree;
import javaBean.bean.BeanTwo;
import javaBean.bean.BeanThreeSetValue;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

@Configuration
@ComponentScan
//@SpringBootApplication
public class SpringDemo {




    /**
     * 写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）, 提交到 GitHub。
     */
    public static void main(String[] args) {
        String xmlPath = "applicationContext.xml";
        //第一种通过applicationContext 获取bean
        ApplicationContext context = new ClassPathXmlApplicationContext(xmlPath);
        BeanOne beanOne = (BeanOne) context.getBean("beanOne");
        System.out.println("BeanOne:" + beanOne.toString());


        //第二种 ClassPathResource
        ClassPathResource resource_ClassPath = new ClassPathResource(xmlPath);
        BeanFactory factory_ClassPath = new XmlBeanFactory(resource_ClassPath);
        BeanOne beanOne_ClassPath = (BeanOne) factory_ClassPath.getBean("beanOne");
        System.out.println("beanOne_ClassPath:" + beanOne_ClassPath.toString());


        //第三种 使用 Component 声明beanTwo @Configuration和@ComponentScan 扫描本包和子包所有的bean，
        ApplicationContext  context1 = new AnnotationConfigApplicationContext(SpringDemo.class);
        BeanTwo beanTwo = context1.getBean(BeanTwo.class);
        beanTwo.setName("beanTwoName");
        System.out.println("BeanOne:" + beanTwo.toString());

        //第四种 自定义 @bean()  + @@Autowired 方式
        BeanThree beanThree= BeanThreeSetValue.beanThree;
        System.out.println("beanThree:" + beanThree.toString());

//        第五种 Resource
//
//        第六种 注入配置，@PropertySource("app.properties") +// 表示读取classpath的app.properties
//        @Value("${app.zone:Z}")
//
//        第七种 条件装配
//        给前面课程提供的 Student/Klass/School 实现自动配置和 Starter。
    }
}
