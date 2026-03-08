package com.example.userserviceproject.model.mapper;

import com.example.userserviceproject.entity.User;
import com.example.userserviceproject.model.UserDtoGet;
import com.example.userserviceproject.model.UserDtoUpdate;

public class Mapper {
    public User dtoGetToUser(UserDtoGet userDTOGet){
        User user = new User();
        user.setNationalId(userDTOGet.getNationalId());
        user.setFirstName(userDTOGet.getFirstName());
        user.setLastName(userDTOGet.getLastName());
        user.setEmail(userDTOGet.getEmail());
        user.setPhoneNumber(userDTOGet.getPhoneNumber());
        user.setBirthDate(userDTOGet.getBirthDate());
        return user;
    }
    public UserDtoGet userToDtoGet(User user){
        UserDtoGet userDTOGet = new UserDtoGet();
        userDTOGet.setNationalId(user.getNationalId());
        userDTOGet.setFirstName(user.getFirstName());
        userDTOGet.setLastName(user.getLastName());
        userDTOGet.setEmail(user.getEmail());
        userDTOGet.setPhoneNumber(user.getPhoneNumber());
        userDTOGet.setBirthDate(user.getBirthDate());
        return userDTOGet;
    }

    public User dtoUpdateToUser(UserDtoUpdate userDtoUpdate){
        User user = new User();
        user.setFirstName(userDtoUpdate.getFirstName());
        user.setLastName(userDtoUpdate.getLastName());
        user.setEmail(userDtoUpdate.getEmail());
        user.setPhoneNumber(userDtoUpdate.getPhoneNumber());
        user.setBirthDate(userDtoUpdate.getBirthDate());
        return user;
    }
}
