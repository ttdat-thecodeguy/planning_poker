package com.springboot.planning_poker.controller;

import com.opencsv.exceptions.CsvValidationException;
import com.springboot.planning_poker.model.business.ITableIssue;
import com.springboot.planning_poker.model.business.impl.IssueService;
import com.springboot.planning_poker.model.enity.Issue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/api/issue")
@Slf4j
public class IssueController {

    @Autowired private ITableIssue issueBus;

    @GetMapping(value = "")
    public ResponseEntity<?> getAllIssuesByTableId(@RequestParam String tableId){
        return ResponseEntity.ok(issueBus.findIssueByGameTableId(tableId));
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addIssue(@RequestParam() String tableId, @RequestBody Issue issue) throws ResponseStatusException {
        Issue savedIssue = issueBus.addIssue(issue, tableId);
        return ResponseEntity.ok(savedIssue);
    }

    @PostMapping(value = "/import-as-csv")
    public ResponseEntity<?> uploadAsCSV(@RequestParam("file") MultipartFile file,
                                         @RequestParam() String tableId,
                                         @RequestParam boolean isIncludeHeader) throws IOException, CsvValidationException {
        List<Issue> lst
                = issueBus.importIssueFromCSVAndGetListIssue(file, tableId, isIncludeHeader);
        return ResponseEntity.ok(lst);
    }

    @PostMapping(value = "/import-as-urls")
    public ResponseEntity<?> uploadAsUrls(@RequestBody List<String> issues, @RequestParam() String tableId){
        return ResponseEntity.ok(issueBus.importFromUrls(issues, tableId));
    }

    @DeleteMapping(value = "/delete-all")
    public ResponseEntity<?> deleteAllIssue(@RequestParam() String tableId){
        issueBus.deleteAllIssue(tableId);
        return ResponseEntity.accepted().build();
    }
}
