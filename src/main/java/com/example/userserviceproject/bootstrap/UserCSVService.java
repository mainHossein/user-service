package com.example.userserviceproject.bootstrap;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public interface UserCSVService {
    List<UserCSVRecord> convertToCsv(File csvFile);
}
