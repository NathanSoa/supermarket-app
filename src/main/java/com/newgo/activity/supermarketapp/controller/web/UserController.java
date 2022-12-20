package com.newgo.activity.supermarketapp.controller.web;

import com.newgo.activity.supermarketapp.dtos.ProductItemRequest;
import com.newgo.activity.supermarketapp.service.ProductItemService;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final ProductItemService productItemService;

    public UserController(ProductItemService productItemService) {
        this.productItemService = productItemService;
    }

    @RequestMapping("/index")
    public String index() {
        return "user/index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, Principal principal) {
        model.addAttribute("products", productItemService.findAll(principal.getName()));
        return "user/list";
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public String changeQuantity(Model model, Principal principal, @PathVariable Long id) {
        model.addAttribute("product", productItemService.findByProductIdAndUser(principal.getName(), id));
        return "user/product";
    }

    @Transactional
    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    public String edit(Model model, Principal principal, @PathVariable Long id){
        productItemService.deleteProduct(principal.getName(), id);
        model.addAttribute("products", productItemService.findAll(principal.getName()));
        return "user/list";
    }

    @Transactional
    @RequestMapping(value = "product", method = RequestMethod.PATCH)
    public String changeQuantity(Model model, Principal principal, @RequestParam String name, @RequestParam Integer quantity) {
        ProductItemRequest request = new ProductItemRequest();
        request.setName(name);
        request.setQuantity(quantity);
        System.out.println("PUT was requested");
        productItemService.changeQuantity(principal.getName(), request);
        model.addAttribute("products", productItemService.findAll(principal.getName()));
        return "user/list";
    }

}
