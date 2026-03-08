package com.example.userserviceproject.service;

import com.example.userserviceproject.model.UserDtoGet;
import com.example.userserviceproject.model.UserDtoUpdate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserService {
    UserDtoGet findById(UUID id);
    UserDtoGet save(UserDtoGet userDTOGet);
    UserDtoGet put(UUID id, UserDtoUpdate updated);
    UserDtoGet patch(UUID id, UserDtoUpdate updatedUser);
    void delete(UUID id);
    UUID getUserId(Long nationalId);
}
