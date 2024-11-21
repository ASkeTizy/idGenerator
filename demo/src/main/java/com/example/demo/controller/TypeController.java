package com.example.demo.controller;

import com.example.demo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TypeController {
    @Autowired
    TypeService typeService;
    @GetMapping(value = "/getId/{role}")
    public Long getSequenceData(@PathVariable String role) {

        Long id = typeService.generateId(role);
        return id;
    }
}
