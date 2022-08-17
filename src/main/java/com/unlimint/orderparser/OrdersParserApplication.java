package com.unlimint.orderparser;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.unlimint.orderparser.csvprocessing.CsvProcessing;
import com.unlimint.orderparser.jsonprocessing.JsonProcessing;

@SpringBootApplication
public class OrdersParserApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdersParserApplication.class, args);
		// parsing csv file
		String csvPath = args[0];
		Path myPath = Paths.get(csvPath);
		CsvProcessing.processingCsv(csvPath, myPath.getFileName().toString());

		// processing json
		String jsonPath = args[1];
		Path jPath = Paths.get(jsonPath);
		JsonProcessing json = new JsonProcessing();
		json.processingJson(jsonPath, jPath.getFileName().toString());
	}

}
