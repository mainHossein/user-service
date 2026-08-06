package com.example.userserviceproject.bootstrap;

import com.example.userserviceproject.entity.User;
import com.example.userserviceproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
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

    public void loadData() throws IOException {
        AtomicInteger count = new AtomicInteger();
        ClassPathResource resource = new ClassPathResource("test-data-unique.csv");
        List<UserCSVRecord> records = userCSVService.convertToCsv(resource.getInputStream());
        for (UserCSVRecord record : records) {
            if (count.get() < 1000) {
                userRepository.save(User.builder()
                        .nationalId(record.getNationalId())
                        .firstName(record.getFirstName())
                        .lastName(record.getLastName())
                        .phoneNumber(record.getPhoneNumber())
                        .birthDate(record.getBirthDate())
                        .email(record.getEmail())
                        .build());
                System.out.println(count.incrementAndGet() + " user added...");
            }
        }

    }
}
