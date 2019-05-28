package com.yitaqi.v2;

import com.yitaqi.v2.model.to.Foo;
import com.yitaqi.v2.service.FooService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author meijunjie
 * @date 2019/5/28
 */
public class Boot {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/v2/context.xml");

        FooService fooService = context.getBean(FooService.class);



//        fooService.insertFoo(new Foo());



//        fooService.updateFoo(new Foo());

//        fooService.getFoo("");

        fooService.getFoo("","");
    }
}
