package com.bczb.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
}
