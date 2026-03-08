package com.example.userserviceproject.repository;

import com.example.userserviceproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByNationalId(Long nationalId);

}

