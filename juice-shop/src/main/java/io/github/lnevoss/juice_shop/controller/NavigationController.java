package io.github.lnevoss.juice_shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// index.html
// about.html
// menu.html
// locations.html
// contact.html
// cart.html
// faq.html
// privacy.html
// terms.html

@Controller
public class NavigationController {
    @GetMapping("/")
    public String index() {
        return "index.html";
    }
    @GetMapping("/about")
    public String about() {
        return "index.html";
    }
    @GetMapping("/menu")
    public String menu() {
        return "index.html";
    }
    @GetMapping("/locations")
    public String locations() {
        return "index.html";
    }
    @GetMapping("/contact")
    public String contact() {
        return "index.html";
    }
    @GetMapping("/cart")
    public String cart() {
        return "index.html";
    }
    @GetMapping("/faq")
    public String faq() {
        return "index.html";
    }
    @GetMapping("/privacy")
    public String privacy() {
        return "index.html";
    }
    @GetMapping("/terms")
    public String terms() {
        return "index.html";
    }
}