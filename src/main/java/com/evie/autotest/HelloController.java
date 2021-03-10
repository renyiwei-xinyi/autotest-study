package com.evie.autotest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ryw@xinyi
 */
@RestController
public class HelloController {

    @RequestMapping(value = {"/hello"}, method = RequestMethod.GET)
    public String helloName() {
        return "myService.getName()";
    }
}
