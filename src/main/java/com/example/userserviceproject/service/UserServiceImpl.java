package com.example.userserviceproject.service;

import com.example.userserviceproject.entity.User;
import com.example.userserviceproject.model.UserDtoGetAndPost;
import com.example.userserviceproject.model.UserDtoUpdate;
import com.example.userserviceproject.model.mapper.Mapper;
import com.example.userserviceproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Mapper mapper = new Mapper();

    @Cacheable(cacheNames = "userCache", key = "#id")
    @Override
    public UserDtoGetAndPost findById(UUID id) {
            return mapper.userToDtoGet(Objects.requireNonNull(userRepository.findById(id).orElse(null)));
    }

    @Override
    public UserDtoGetAndPost save(UserDtoGetAndPost userDTOGetAndPost) {
        return mapper.userToDtoGet(userRepository.save(mapper.dtoGetToUser(userDTOGetAndPost)));
    }

    @Transactional
    @Override
    public UserDtoGetAndPost put(UUID id, UserDtoUpdate updated) {
        User updatedUser = mapper.dtoUpdateToUser(updated);
        User fetched = userRepository.getReferenceById(id);
        fetched.setFirstName(updatedUser.getFirstName());
        fetched.setLastName(updatedUser.getLastName());
        fetched.setEmail(updatedUser.getEmail());
        fetched.setPhoneNumber(updatedUser.getPhoneNumber());
        fetched.setBirthDate(updatedUser.getBirthDate());
        return mapper.userToDtoGet(userRepository.save(fetched));
    }

    @Transactional
    @Override
    public UserDtoGetAndPost patch(UUID id, UserDtoUpdate updatedUser) {
        User fetcheduser = userRepository.getReferenceById(id);
        if (updatedUser.getFirstName() != null) {
            fetcheduser.setFirstName(updatedUser.getFirstName());
        }
        if (updatedUser.getLastName() != null) {
            fetcheduser.setLastName(updatedUser.getLastName());
        }
        if (updatedUser.getEmail() != null) {
            fetcheduser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPhoneNumber() != null) {
            fetcheduser.setPhoneNumber(updatedUser.getPhoneNumber());
        }
        if (updatedUser.getBirthDate() != null) {
            fetcheduser.setBirthDate(updatedUser.getBirthDate());
        }
        return mapper.userToDtoGet(userRepository.save(fetcheduser));
    }

    @Override
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public UUID getUserId(Long nationalId) {
        User byNationalId = userRepository.findByNationalId(nationalId);
        if (byNationalId != null) {
            return byNationalId.getId();
        } else {
            return null;
        }
    }

    @Override
    public Boolean checkUserExists(long nationalId) {
        return userRepository.existsByNationalId(nationalId);
    }
}
