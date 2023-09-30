package com.vulnerable.api.controller;

import com.vulnerable.api.model.Country;
import com.vulnerable.api.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CountryController {

    @Autowired
    private DBService dbService;

    @GetMapping("/countries")
    public List<Country> getAll() {
        return dbService.selectAll();
    }

    @GetMapping("/countries/{id}")
    public Country get(@PathVariable("id") String id) {
        return dbService.select(id);
    }
}
