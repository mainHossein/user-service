package com.example.userserviceproject.service;

import com.example.userserviceproject.model.UserDtoGetAndPost;
import com.example.userserviceproject.model.UserDtoUpdate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserService {
    UserDtoGetAndPost findById(UUID id);
    UserDtoGetAndPost save(UserDtoGetAndPost userDTOGetAndPost);
    UserDtoGetAndPost put(UUID id, UserDtoUpdate updated);
    UserDtoGetAndPost patch(UUID id, UserDtoUpdate updatedUser);
    void delete(UUID id);
    UUID getUserId(Long nationalId);
    Boolean checkUserExists(long nationalId);
}
