package com.example.userserviceproject.service;

import com.example.userserviceproject.model.UserDtoGet;
import com.example.userserviceproject.model.UserDtoPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserService {
    Page<UserDtoGet> findAll(Pageable pageable);
    UserDtoGet findById(UUID id);
    UserDtoGet save(UserDtoGet userDTOGet);
    UserDtoPost put(UUID id, UserDtoPost updated);
    UserDtoPost patch(UUID id, UserDtoPost updatedUser);
    void delete(UUID id);
    UUID getUserId(Long nationalId);
}
