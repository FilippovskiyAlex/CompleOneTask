package com.example.TestTwo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SayHelloController {

    @GetMapping("/Hello/{name}")
    //@PreAuthorize("hasAuthority('read')")
    public String HelloUser(@PathVariable String name) {
        return "Hello, " + name + "!";
    }

}
