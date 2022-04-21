package com.springboot.planning_poker.controller;

import com.opencsv.exceptions.CsvValidationException;
import com.springboot.planning_poker.model.business.IUser;
import com.springboot.planning_poker.model.enity.User;
import com.springboot.planning_poker.model.payload.request.LoginRequest;
import com.springboot.planning_poker.model.payload.response.LoginResponse;
import com.springboot.planning_poker.model.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping(value = "/api") @RequiredArgsConstructor
public class HomeController {

     private final AuthenticationManager authenticationManager;
     private final IUser userBus;
     private final JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user){
        return ResponseEntity.ok(userBus.signup(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest userLogin){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword()));
        return ResponseEntity.ok(new LoginResponse(jwtUtils.generateToken(userLogin.getEmail())));
    @PostMapping("/issue/import-csv")
    public ResponseEntity<?> importAsCSV(MultipartFile file) throws IOException, CsvValidationException{
		File f = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(file.getBytes());
		fos.close();
		issues.downloadIssuesAsCSV(f);
		return ResponseEntity.ok("Import done");
    }
}





