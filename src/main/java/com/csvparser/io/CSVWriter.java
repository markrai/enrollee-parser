package com.csvparser.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.csvparser.constants.ParserConstants;
import com.csvparser.entity.Enrollee;

public class CSVWriter {
	private static final String CSV_FILE_PATH = "./csv/generated/";
	private static final String EXTENSION = ".csv";

	private static final String CSV_FILE_HEADERS[] = { ParserConstants.USER_ID, ParserConstants.FIRST_NAME,
			ParserConstants.LAST_NAME, ParserConstants.VERSION, ParserConstants.INSURANCE_COMPANY };

	public static void writeFile(Map<String, Enrollee> map) {

		// extract data from Hashmap
		map.forEach((key, userValue) -> {

			try {
				// company name for new file name
				String fileName = userValue.getInsuranceCompany();
				String fullPath = CSV_FILE_PATH + File.separator + fileName + EXTENSION;

				// add header to csv file
				CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader(CSV_FILE_HEADERS).withIgnoreEmptyLines();

				// check if a csv file exists; skip header
				File csvFile = new File(fullPath);
				if (csvFile.exists()) {
					csvFormat = CSVFormat.DEFAULT.withSkipHeaderRecord(true);
				}

				// create writer object for csv - append data if file exists
				BufferedWriter writer = Files.newBufferedWriter(Paths.get(fullPath), StandardOpenOption.APPEND,
						StandardOpenOption.CREATE);

				// adding a row to the csv file from enrollee object
				CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat);
				csvPrinter.printRecord(userValue.getUserId(), userValue.getFirstName(), userValue.getLastName(),
						userValue.getVersion(), userValue.getInsuranceCompany());

				csvPrinter.flush();

				System.out.println("Record for " + fileName + "  has been created");

			} catch (IOException e) {
				e.printStackTrace();
			}

		});

		System.out.println("\n*** Generated CSVs can be found in the `/csv/generated` folder ***");
		System.out.println("*** Press F5 to refresh directory in the Eclipse package explorer ☺ ***");
	}
}