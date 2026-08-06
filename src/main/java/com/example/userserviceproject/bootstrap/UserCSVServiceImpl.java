package com.example.userserviceproject.bootstrap;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class UserCSVServiceImpl implements UserCSVService {
    @Override
    public List<UserCSVRecord> convertToCsv(InputStream inputStream) {
        try(InputStreamReader reader = new InputStreamReader(inputStream)) {
            return new CsvToBeanBuilder<UserCSVRecord>(reader)
                    .withType(UserCSVRecord.class).build().parse();
        }catch (IOException e) {
            throw new RuntimeException("Failed to read csv",e);
        }
    }
}
