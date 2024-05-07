package com.example.ibteam7.controller;

import com.example.ibteam7.config.Constants;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = Constants.FRONTEND_URL)
public class NonVersionController {
    @GetMapping("/")
    public String base() {
        return "Welcome to the IB Team 7 server base URL";
    }


}
