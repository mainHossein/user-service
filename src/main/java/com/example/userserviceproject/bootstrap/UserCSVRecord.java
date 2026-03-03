package com.example.userserviceproject.bootstrap;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
public class UserCSVRecord {
    @CsvBindByName
    private Long nationalId;
    @CsvBindByName
    private String firstName;
    @CsvBindByName
    private String lastName;
    @CsvBindByName
    private String email;
    @CsvBindByName
    private String phoneNumber;
    @CsvBindByName
    private Date birthDate;
}
