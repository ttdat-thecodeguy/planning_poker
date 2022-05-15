package com.springboot.planning_poker.model.business.impl;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.springboot.planning_poker.model.business.ITable;
import com.springboot.planning_poker.model.definition.StatusCode;
import com.springboot.planning_poker.model.enity.GameTable;
import com.springboot.planning_poker.model.responsitory.TableRepo;
import com.springboot.planning_poker.model.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.springboot.planning_poker.model.business.ITableIssue;
import com.springboot.planning_poker.model.enity.Issue;
import com.springboot.planning_poker.model.responsitory.IssueRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@Service
public class IssueService implements ITableIssue{
	
	@Autowired private IssueRepo issueRepo;
	@Autowired private TableRepo tableRepo;
	@Autowired private ITable tableBus;

	@Override
	public Issue findById(String id) {
		Issue issue = issueRepo.findById(id).orElse(null);
		if(issue == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, StatusCode.ISSUE_NOT_FOUND);
		return issue;
	}

	@Override
	public List<Issue> findIssueByGameTableId(String tableId) {
		return issueRepo.findByTableId(tableId);
	}

	@Override
	public Issue addIssue(Issue issue, String tableId) throws ResponseStatusException {
		GameTable table = this.tableBus.findTableById(tableId);
		issue.setGameTable(table);
		return issueRepo.save(issue);
	}

	@Override
	public void importFromJira() {
		// TODO Auto-generated method stub
	}


	@Override @Transactional
	public List<Issue> importFromUrls(List<String> urls, String tableId) {
		GameTable table = tableRepo.getById(tableId);
		return urls
				.stream()
				.map(url -> issueRepo.save(new Issue(null, url, url, null, table, null)))
				.collect(Collectors.toList());
	}

	@Override
	public void deleteAllIssue(String tableId) {
		GameTable table = this.tableBus.findTableById(tableId);
		table.getIssues().clear();
	}

	@Override
	public void updateResultToIssue( String id, String storyPoint) {
		Issue issue = this.findById(id);
		issue.setStoryPoint(storyPoint);
		issueRepo.save(issue);
	}

	@Override
	public List<Issue> importIssueFromCSVAndGetListIssue(File file, String tableId, boolean isNotIncludeHeader) throws CsvValidationException, IOException {
		CSVReader reader;
		CSVReaderBuilder csvBuilder = new CSVReaderBuilder(new FileReader(file));

		if(isNotIncludeHeader){
			reader = csvBuilder.withSkipLines(1).build();
		} else {
			reader = csvBuilder.build();
		}
		GameTable table = tableRepo.getById(tableId);
		String[] nextRecord;
		List<Issue> issues = new LinkedList<>();
		while((nextRecord = reader.readNext()) != null) {
			String name = Objects.equals(nextRecord[0], "") ? null : nextRecord[0];
			String desc = Objects.equals(nextRecord[2], "") ? null : nextRecord[2];
			String link = Objects.equals(nextRecord[3], "") ? null : nextRecord[3];
			String storyPoint = Objects.equals(nextRecord[4], "") ? null : nextRecord[4];
			issues.add(issueRepo.save( new Issue(null, name, link, desc,table, storyPoint)));
		}
		return issues;
	}
}
