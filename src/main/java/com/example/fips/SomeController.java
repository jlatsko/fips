package com.example.fips;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("fips")
public class SomeController {
    @GetMapping
    public Iterable<String> get() {
        return List.of("Hello"," FIPS", " World");
    }
}
