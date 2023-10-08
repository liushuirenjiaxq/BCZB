package com.bczb.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/test")
    public String to_test() {
        return "test";
    }

    @GetMapping("/main")
    public String to_main() {
        return "main";
    }

    @GetMapping("/index")
    public String to_index() {
        return "index";
    }

    @GetMapping("/exp_list")
    public String exp_list() {
        return "exp_list";
    }

    @GetMapping("/exp_detail")
    public String exp_detail() {
        return "exp_detail";
    }
}
