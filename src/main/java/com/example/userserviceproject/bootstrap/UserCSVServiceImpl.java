package com.example.userserviceproject.bootstrap;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
public class UserCSVServiceImpl implements UserCSVService {
    @Override
    public List<UserCSVRecord> convertToCsv(File csvFile) {
        try {
            return new CsvToBeanBuilder<UserCSVRecord>(new FileReader(csvFile))
                    .withType(UserCSVRecord.class).build().parse();
        }catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
