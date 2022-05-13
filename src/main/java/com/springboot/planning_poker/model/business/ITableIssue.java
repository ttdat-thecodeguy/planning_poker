package com.springboot.planning_poker.model.business;

import com.opencsv.exceptions.CsvValidationException;
import com.springboot.planning_poker.model.enity.Issue;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ITableIssue {
     Issue findById(String id);
     List<Issue> findIssueByGameTableId(String tableId);
     Issue addIssue(Issue issue, String tableId) throws ResponseStatusException;
     void importFromJira();
     List<Issue> importIssueFromCSVAndGetListIssue(MultipartFile file, String tableId, boolean isIncludeHeader) throws CsvValidationException, IOException;
     List<Issue> importFromUrls(List<String> urls, String tableId);
     void deleteAllIssue(String tableId);
     void updateResultToIssue(String id, String storyPoint);
}
