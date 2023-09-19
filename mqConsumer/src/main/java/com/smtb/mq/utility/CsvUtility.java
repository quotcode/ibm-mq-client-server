package com.smtb.mq.utility;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CsvUtility {
    private static final Logger logger = LogManager.getLogger(CsvUtility.class);

    // reads a csv file and returns csv contents in String[]
    public String csvReader(File csvFile) {
        String result;
        List<String> resultList = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            reader.readAll().forEach(ele -> resultList.add(Arrays.toString(ele)));
            logger.info("resultList: " + resultList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
        return resultList.toString();
    }

    public String convertCSVToString(File csvFile) throws IOException {
        logger.info("convertCSVToString(): method entry");
        // normal way of rading a csv file
        // String csvContent = Files.readString(Paths.get(csvFile.toString()));

        // using openCSV library for reading csv file
        String csvContent = csvReader(csvFile);
        return csvContent;
    }
}
