package com.springboot.planning_poker.model.business.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.springboot.planning_poker.model.business.ITableIssue;
import com.springboot.planning_poker.model.enity.Issue;
import com.springboot.planning_poker.model.responsitory.IssueRepo;

@Component
public class IssueService implements ITableIssue{
	
	@Autowired private IssueRepo issueRepo;
	
	@Override
	public void addIssue() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void importFromJira() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void importFromCSV() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void importFromUrls(String[] urls) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void downloadIssuesAsCSV(File file) throws CsvValidationException, IOException {
				
		CSVReader csvReader = new CSVReaderBuilder(new FileReader(file))
											.withSkipLines(1)
											.build();
		String[] nextRecord;
		while((nextRecord = csvReader.readNext()) != null) {
			String name = nextRecord[0] == "" ? null : nextRecord[0];
			String key = nextRecord[1] == "" ? null : nextRecord[1];
			String desc = nextRecord[2] == "" ? null : nextRecord[2];
			String link = nextRecord[3] == "" ? null : nextRecord[3];
			String storyPoint = nextRecord[4] == "" ? null : nextRecord[4];
			Issue issue = new Issue(null, name, link, desc, storyPoint);
			issueRepo.save(issue);
		}
	}

}
