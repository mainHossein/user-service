package com.example.userserviceproject.model.mapper;

import com.example.userserviceproject.entity.User;
import com.example.userserviceproject.model.UserDtoGet;
import com.example.userserviceproject.model.UserDtoPost;

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

    public User dtoPostToUser(UserDtoPost userDtoPost){
        User user = new User();
        user.setFirstName(userDtoPost.getFirstName());
        user.setLastName(userDtoPost.getLastName());
        user.setEmail(userDtoPost.getEmail());
        user.setPhoneNumber(userDtoPost.getPhoneNumber());
        user.setBirthDate(userDtoPost.getBirthDate());
        return user;
    }
    public UserDtoPost userToDtoPost(User user){
        UserDtoPost userDtoPost = new UserDtoPost();
        userDtoPost.setFirstName(user.getFirstName());
        userDtoPost.setLastName(user.getLastName());
        userDtoPost.setEmail(user.getEmail());
        userDtoPost.setPhoneNumber(user.getPhoneNumber());
        userDtoPost.setBirthDate(user.getBirthDate());
        return userDtoPost;
    }
}
