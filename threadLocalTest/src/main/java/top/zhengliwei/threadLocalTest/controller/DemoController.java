package top.zhengliwei.threadLocalTest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zhengliwei.threadLocalTest.model.BadRequestContext;
import top.zhengliwei.threadLocalTest.model.RequestContext;


@RestController
@RequestMapping
public class DemoController {

    @RequestMapping("/hello")
    public String sayHello() throws InterruptedException {
        return "hello: " + RequestContext.getUserSession().getUserName();
//        Thread.sleep(5000);
//        return "hello: " + BadRequestContext.getUserSession().getUserName();
    }
}
