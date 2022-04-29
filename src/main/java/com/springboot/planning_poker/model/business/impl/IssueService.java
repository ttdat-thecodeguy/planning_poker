package com.springboot.planning_poker.model.business.impl;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import com.springboot.planning_poker.model.enity.GameTable;
import com.springboot.planning_poker.model.responsitory.TableRepo;
import com.springboot.planning_poker.model.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class IssueService implements ITableIssue{
	
	@Autowired private IssueRepo issueRepo;
	@Autowired private TableRepo tableRepo;

	@Override
	public List<Issue> findIssueByGameTableId(String tableId) {
		return issueRepo.findByTableId(tableId);
	}

	@Override
	public Issue addIssue(Issue issue, String tableId) throws ResponseStatusException {
		// TODO Auto-generated method stub
		GameTable table = tableRepo.findById(tableId).orElse(null);
		if(table == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Table Not in DB");
		issue.setGameTable(table);
		return issueRepo.save(issue);
	}

	@Override
	public void importFromJira() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void importFromUrls(String[] urls) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteAllIssue(String tableId) {
		GameTable table = tableRepo.findById(tableId).orElse(null);
		if(table == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Table Not in DB");
		table.getIssues().clear();
	}

	@Override
	public List<Issue> importIssueFromCSVAndGetListIssue(MultipartFile file, String tableId, boolean isIncludeHeader) throws CsvValidationException, IOException {
		File f = Utils.transferToFile(file);
		CSVReader reader;
		CSVReaderBuilder csvBuilder = new CSVReaderBuilder(new FileReader(f));

		if(isIncludeHeader){
			reader = csvBuilder.withSkipLines(1).build();
		} else {
			reader = csvBuilder.build();
		}


		GameTable table = tableRepo.getById(tableId);
		String[] nextRecord;
		List<Issue> issues = new LinkedList<>();
		while((nextRecord = reader.readNext()) != null) {
			String name = nextRecord[0] == "" ? null : nextRecord[0];
			String desc = nextRecord[2] == "" ? null : nextRecord[2];
			String link = nextRecord[3] == "" ? null : nextRecord[3];
			String storyPoint = nextRecord[4] == "" ? null : nextRecord[4];
			Issue issue = new Issue(null, name, link, desc,table, storyPoint);
			issueRepo.save(issue);
			issues.add(issue);
		}
		return issues;
	}



}
