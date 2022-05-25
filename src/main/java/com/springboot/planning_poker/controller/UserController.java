package com.springboot.planning_poker.controller;

import com.springboot.planning_poker.model.business.IUser;
import com.springboot.planning_poker.model.payload.request.UserUpdateRequest;
import com.springboot.planning_poker.model.payload.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user")
@Slf4j
public class UserController {
    @Autowired private IUser userBus;
    @Autowired private UserResponse response;

    @GetMapping(value = "/")
    public ResponseEntity<?> getUsers() {
        log.info(response.getToken());
        return ResponseEntity.ok().body(userBus.getUsers());
    }

    //PUT HTTP method is used to modify/update a resource where the client sends data that updates the entire resource.
    @PutMapping(value = "/update")
    public ResponseEntity<?> updateUser(UserUpdateRequest user) throws Exception {
        userBus.updateUser(user);
        return ResponseEntity.ok("User Updated");
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteUser(@RequestParam Long id) {
        userBus.deleteUser(id);
        return ResponseEntity.ok("User Deleted");
    }

}
