package com.springboot.planning_poker.controller;

import com.springboot.planning_poker.model.business.IUser;
import com.springboot.planning_poker.model.payload.request.UserUpdateRequest;
import com.springboot.planning_poker.model.payload.response.LoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user")
@Slf4j

/*
- cơ chế game revelel -> nhấn vô sẽ lấy toàn bộ thông tin điểm của các thành viên
    - default -> tất cả thành viên
    - set
cơ chế tải ảnh : send picture to frontend -> edit it-> send to storage
 */
public class UserController {
    @Autowired private IUser userBus;
    @Autowired private LoginResponse response;
    @GetMapping(value = "/")
    public ResponseEntity<?> getUsers() {
        log.info(response.getToken());
        return ResponseEntity.ok().body(userBus.getUsers());
    }

    //PUT HTTP method is used to modify/update a resource where the client sends data that updates the entire resource.
    @PutMapping(value = "/update")
    public ResponseEntity<?> updateUser(UserUpdateRequest user) {
        userBus.updateUser(user);
        return ResponseEntity.ok("User Updated");
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteUser(@RequestParam Long id) {
        userBus.deleteUser(id);
        return ResponseEntity.ok("User Deleted");
    }

}