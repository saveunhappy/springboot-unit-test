package com.luv2code.junitdemo.controller;

import com.luv2code.junitdemo.domain.Dog;
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
