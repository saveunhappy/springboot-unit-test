package com.luv2code.springbootunittest.controller;

import com.luv2code.springbootunittest.domain.Dog;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @PostMapping("/dog/show/entity")
    public Dog getDog(@RequestBody Dog dog){
        return dog;
    }
}
