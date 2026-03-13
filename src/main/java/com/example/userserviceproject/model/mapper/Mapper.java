package com.example.userserviceproject.model.mapper;

import com.example.userserviceproject.entity.User;
import com.example.userserviceproject.model.UserDtoGetAndPost;
import com.example.userserviceproject.model.UserDtoUpdate;

public class Mapper {
    public User dtoGetToUser(UserDtoGetAndPost userDTOGetAndPost){
        User user = new User();
        user.setNationalId(userDTOGetAndPost.getNationalId());
        user.setFirstName(userDTOGetAndPost.getFirstName());
        user.setLastName(userDTOGetAndPost.getLastName());
        user.setEmail(userDTOGetAndPost.getEmail());
        user.setPhoneNumber(userDTOGetAndPost.getPhoneNumber());
        user.setBirthDate(userDTOGetAndPost.getBirthDate());
        return user;
    }
    public UserDtoGetAndPost userToDtoGet(User user){
        UserDtoGetAndPost userDTOGetAndPost = new UserDtoGetAndPost();
        userDTOGetAndPost.setNationalId(user.getNationalId());
        userDTOGetAndPost.setFirstName(user.getFirstName());
        userDTOGetAndPost.setLastName(user.getLastName());
        userDTOGetAndPost.setEmail(user.getEmail());
        userDTOGetAndPost.setPhoneNumber(user.getPhoneNumber());
        userDTOGetAndPost.setBirthDate(user.getBirthDate());
        return userDTOGetAndPost;
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
