package com.unlimint.orderparser.csvprocessing;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.unlimint.orderparser.entity.Order;
import lombok.NoArgsConstructor;
import lombok.var;

@NoArgsConstructor
@Component
public class CsvProcessing {

	public static AtomicInteger atomicInteger = new AtomicInteger(0);

	public static void processingCsv(String csvFilePath, String fileName) {
		ArrayList<Order> orderList = new ArrayList<>();

		try {
			Path myPath = Paths.get(csvFilePath);

			CSVParser parser = new CSVParserBuilder().withSeparator(',').build();

			try (var br = Files.newBufferedReader(myPath, StandardCharsets.UTF_8);
					var reader = new CSVReaderBuilder(br).withCSVParser(parser).build()) {

				List<String[]> rows = reader.readAll();
				for (int i = 1; i < rows.size(); i++) {
					String[] line = rows.get(i);
					orderList.add(new Order(atomicInteger.incrementAndGet(), Long.valueOf(line[0]),
							Double.valueOf(line[1]), line[2], line[3], fileName, i, HttpStatus.OK));
				}
			}
			ObjectMapper mapper = new ObjectMapper();
			for (int j = 0; j < orderList.size(); j++) {
				System.out.println(mapper.writeValueAsString(orderList.get(j)));
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
