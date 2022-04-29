package com.springboot.planning_poker.model.business;

import com.opencsv.exceptions.CsvValidationException;
import com.springboot.planning_poker.model.enity.Issue;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ITableIssue {
    public List<Issue> findIssueByGameTableId(String tableId);
    public Issue addIssue(Issue issue, String tableId) throws ResponseStatusException;
    public void importFromJira();
    public List<Issue> importIssueFromCSVAndGetListIssue(MultipartFile file, String tableId, boolean isIncludeHeader) throws CsvValidationException, IOException;
    public void importFromUrls(String[] urls);
    public void deleteAllIssue(String tableId);
}
