package com.csvparser.io;

import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.csvparser.constants.ParserConstants;
import com.csvparser.entity.Enrollee;

public class CSVReader {

	private static final String FILE_NAME = "sampleData.csv";
	private static final String EXTENSION = ".csv";
	private static final String BASE_PATH = "./csv";
	private static final String CSV_FILE_PATH = BASE_PATH + "/source/" + FILE_NAME;

	public void parseCSV() {

		try {

			Map<String, Enrollee> map = new HashMap<String, Enrollee>();

			Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH));
			CSVParser csvParser = new CSVParser(reader,
					CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

			// Extract data from csv and assign to object
			csvParser.forEach(csvRecord -> {

				String fullPath = BASE_PATH + "/generated/" + File.separator
						+ csvRecord.get(ParserConstants.INSURANCE_COMPANY) + EXTENSION;
				deleteFile(fullPath);

				int version = Integer.parseInt(csvRecord.get(ParserConstants.VERSION));
				Enrollee enrolleeData = new Enrollee(csvRecord.get(ParserConstants.USER_ID),
						csvRecord.get(ParserConstants.FIRST_NAME), csvRecord.get(ParserConstants.LAST_NAME), version,
						csvRecord.get(ParserConstants.INSURANCE_COMPANY));

				// preparing data for new csv files
				prepareData(map, csvRecord, enrolleeData);

			});

			// Sort data before writing files
			Map<String, Enrollee> sortedData = sortMapData(map);
			// creating new files
			CSVWriter.writeFile(sortedData);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Map<String, Enrollee> sortMapData(Map<String, Enrollee> map) {
		List<Entry<String, Enrollee>> listOfEntry = new LinkedList<>(map.entrySet());
		Collections.sort(listOfEntry,
				(p1, p2) -> p1.getValue().getFirstName().compareToIgnoreCase(p2.getValue().getFirstName()));

		Map<String, Enrollee> sortedData = new LinkedHashMap<>();

		for (Entry<String, Enrollee> entry : listOfEntry) {
			sortedData.put(entry.getKey(), entry.getValue());
		}
		return sortedData;

	}

	private void prepareData(Map<String, Enrollee> map, CSVRecord csvRecord, Enrollee newEnrolleeData) {

		// if map already contains enrollee data
		if (map.containsKey(csvRecord.get(ParserConstants.USER_ID))) {

			Enrollee existingData = map.get(csvRecord.get(ParserConstants.USER_ID));

			// if enrollee version is greater than previous, then update map data
			if (existingData.getVersion() < newEnrolleeData.getVersion()) {
				map.put(csvRecord.get(ParserConstants.USER_ID), newEnrolleeData);
			}

		} else {

			// add new row
			map.put(csvRecord.get(ParserConstants.USER_ID), newEnrolleeData);
		}
	}

	public boolean deleteFile(String filePath) {

		File csvFile = new File(filePath);
		if (csvFile.exists()) {
			return csvFile.delete();
		}
		return false;

	}
}