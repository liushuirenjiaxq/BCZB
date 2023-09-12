package com.bczb.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ViewController {
    @GetMapping("/to_{xxx}")
    public String pageMethod(@PathVariable("xxx") String page){
        System.out.println("test");
        return page;
    }
}
