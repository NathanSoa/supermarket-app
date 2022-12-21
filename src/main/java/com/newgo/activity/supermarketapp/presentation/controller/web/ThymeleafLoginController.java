package com.newgo.activity.supermarketapp.presentation.controller.web;

import com.newgo.activity.supermarketapp.data.entities.RoleName;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ThymeleafLoginController {

    @RequestMapping(value = {"", "/", "/index", "/login"}, method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @RequestMapping("/success")
    public void loginRedirect(HttpServletRequest request, HttpServletResponse response, Authentication authResult)  throws Exception {
        String roles = authResult.getAuthorities().toString();

        if(roles.contains(RoleName.ROLE_USER.toString()))
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/user/index"));
        else if(roles.contains(RoleName.ROLE_ADMINISTRATOR.toString()))
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/admin/index"));
    }
}
