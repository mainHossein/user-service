package com.example.userserviceproject.bootstrap;

import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public interface UserCSVService {
    List<UserCSVRecord> convertToCsv(InputStream inputStream);
}
