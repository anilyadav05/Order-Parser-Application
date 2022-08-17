package com.unlimint.orderparser.jsonprocessing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.Builder;
import com.unlimint.orderparser.csvprocessing.CsvProcessing;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class JsonProcessing {
	
	@Autowired
	CsvProcessing csvProcessing ;
	
	// processing json file
	@SuppressWarnings("static-access")
	public void processingJson(String jsonFilePath,String fileName) {
		Path myPath = Paths.get(jsonFilePath);
		try {
			JsonNode jsonTree = new ObjectMapper().readTree(new File(jsonFilePath));
			Builder csvSchemaBuilder = CsvSchema.builder();
			JsonNode firstObject = jsonTree.elements().next();
			firstObject.fieldNames().forEachRemaining(fieldName -> {
				csvSchemaBuilder.addColumn(fieldName);
			});
			CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
			CsvMapper csvMapper = new CsvMapper();
			csvMapper.writerFor(JsonNode.class).with(csvSchema)
					.writeValue(new File(myPath.getParent() + File.separator + "orders2.csv"), jsonTree);
			// processing csv

			csvProcessing.processingCsv(myPath.getParent() + File.separator + "orders2.csv",fileName);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				Files.delete(Paths.get(myPath.getParent() + File.separator + "orders2.csv"));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
