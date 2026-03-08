package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.OwnerService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/owner")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @GetMapping("/dashboard_details")
    public ResponseEntity<?> getOwnerDashboard(HttpSession session) {

        Integer ownerId = (Integer) session.getAttribute("USER_ID");

        if (ownerId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(
                ownerService.getOwnerDashboard(ownerId)
        );
    }
}
