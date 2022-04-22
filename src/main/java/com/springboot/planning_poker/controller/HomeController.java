package com.springboot.planning_poker.controller;

import com.opencsv.exceptions.CsvValidationException;
import com.springboot.planning_poker.model.business.IUser;
import com.springboot.planning_poker.model.enity.User;
import com.springboot.planning_poker.model.payload.request.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@RestController @RequestMapping(value = "/api") @Slf4j
public class HomeController {
    @Autowired private IUser userBus;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) throws Exception {
        return ResponseEntity.ok(userBus.signup(user));
    }

    @PostMapping("/login") @CrossOrigin(origins="*", maxAge=3600)
    public ResponseEntity<?> login(@RequestBody LoginRequest userLogin) throws Exception {
        log.info("Login");
        return ResponseEntity.ok(userBus.login(userLogin));
    }

    @PostMapping("/signup-as-guest")
    public ResponseEntity<?> signUpAsGuest(@RequestBody User user,
                                           @RequestParam String tableId,
                                           @RequestParam(required = false) Boolean isSpectator) {
        return ResponseEntity.ok(userBus.signupAsGuest(user, isSpectator, tableId));
    }
}





