package com.newgo.activity.supermarketapp.presentation.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/index")
    public String index() {
        return "admin/index";
    }

}