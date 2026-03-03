package com.example.userserviceproject.bootstrap;

import com.example.userserviceproject.entity.User;
import com.example.userserviceproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserCSVService userCSVService;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            LocalTime start = LocalTime.now();
            loadData();
            System.out.println("Started at: " + start);
            System.out.println("Ended at: " + LocalTime.now());
        }
    }

    public void loadData() throws FileNotFoundException {
        AtomicInteger count = new AtomicInteger();
        File file = ResourceUtils.getFile("/media/hossein/New Volume/AI/Documents/test-data-unique.csv");
        List<UserCSVRecord> records = userCSVService.convertToCsv(file);
        records.forEach(userCSVRecord -> {
            userRepository.save(User.builder()
                    .nationalId(userCSVRecord.getNationalId())
                    .firstName(userCSVRecord.getFirstName())
                    .lastName(userCSVRecord.getLastName())
                    .phoneNumber(userCSVRecord.getPhoneNumber())
                    .birthDate(userCSVRecord.getBirthDate())
                    .email(userCSVRecord.getEmail())
                    .build());
            System.out.println(count.incrementAndGet() + " user added...");
        });

    }
}
