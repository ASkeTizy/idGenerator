package com.example.demo.controller;

import com.example.demo.dao.SequenceDAO;
import com.example.demo.entity.SequenceData;
import com.example.demo.service.BookService;
import lombok.Lombok;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TypeController {
    BookService bookService = new BookService();
    SequenceDAO dao = new SequenceDAO();
    @GetMapping(value = "/getId/{role}")
    public Long getSequenceData(@PathVariable String role) {

        Long id = dao.generateId(role);
        return id;
    }
}
