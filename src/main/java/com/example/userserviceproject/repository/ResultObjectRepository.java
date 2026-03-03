package com.example.userserviceproject.repository;

import com.example.userserviceproject.entity.ResultObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ResultObjectRepository extends JpaRepository<ResultObject, UUID> {
}
