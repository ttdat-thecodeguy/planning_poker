package com.springboot.planning_poker.controller;

import com.springboot.planning_poker.model.business.ITable;
import com.springboot.planning_poker.model.enity.GameTable;
import com.springboot.planning_poker.model.payload.request.TableUpdate;
import com.springboot.planning_poker.model.payload.response.UserResponse;
import com.springboot.planning_poker.model.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/table")
@RequiredArgsConstructor
@Slf4j
public class TableController {
    private final ITable tableBus;
    private final UserResponse response;
    private final JwtUtils jwtUtils;

    @PostMapping(value = "/add")
    public ResponseEntity<?> addTable(@Valid @RequestBody GameTable table){
        return ResponseEntity.ok(tableBus.addTable(table, jwtUtils.getUserIdFromToken(response.getToken())));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getTableById(@PathVariable("id") String id ) throws Exception{
       GameTable table = tableBus.getTableById(id);
       if(table == null) throw new Exception("Not found");
       else return ResponseEntity.ok(table);
    }
    /// patch method not working --> need asks
    @PatchMapping(value = "/update-owner")
    public ResponseEntity<?> updateTableOwner(@RequestBody TableUpdate table) throws Exception{
		return ResponseEntity.ok(tableBus.updateTableOwner(table.getUserId(), table.getTableId()));
    }
    
//    @PostMapping(value = "/update-user")
//    public ResponseEntity<?> updateUser(@RequestBody TableUpdate tableUpdate) {
//        tableBus.addUserToTable(tableUpdate);
//        return ResponseEntity.ok(tableUpdate);
//    }
}
