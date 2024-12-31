package org.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App
{

    public static void main(String[] args )
    {

        ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
        Dev dev= context.getBean(Dev.class);
        dev.build();
//dev.setAge(20);
//        System.out.println(dev.getAge());
          // Dev dev = new Dev();
          // dev.build();

    }
}
