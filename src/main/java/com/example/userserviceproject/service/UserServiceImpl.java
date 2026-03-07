package com.example.userserviceproject.service;

import com.example.userserviceproject.entity.User;
import com.example.userserviceproject.model.UserDtoGet;
import com.example.userserviceproject.model.UserDtoUpdate;
import com.example.userserviceproject.model.mapper.Mapper;
import com.example.userserviceproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Mapper mapper = new Mapper();

    @Override
    public Page<UserDtoGet> findAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(mapper::userToDtoGet);
    }

    @Override
    public UserDtoGet findById(UUID id) {
        if (userRepository.existsById(id)) {
            return mapper.userToDtoGet(userRepository.findById(id).get());
        } else {
            return null;
        }
    }

    @Override
    public UserDtoGet save(UserDtoGet userDTOGet) {
        return mapper.userToDtoGet(userRepository.save(mapper.dtoGetToUser(userDTOGet)));
    }

    @Transactional
    @Override
    public UserDtoGet put(UUID id, UserDtoUpdate updated) {
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
    public UserDtoGet patch(UUID id, UserDtoUpdate updatedUser) {
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
}
