package com.example.userserviceproject.controller;

import com.example.userserviceproject.entity.Client;
import com.example.userserviceproject.entity.MetaData;
import com.example.userserviceproject.entity.ResultObject;
import com.example.userserviceproject.entity.Status;
import com.example.userserviceproject.model.UserDtoGet;
import com.example.userserviceproject.model.UserDtoPost;
import com.example.userserviceproject.repository.ResultObjectRepository;
import com.example.userserviceproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ResultObjectRepository resultObjectRepository;


    @GetMapping
    public PagedModel<EntityModel<UserDtoGet>> getUsers(Pageable pageable,
                                                        PagedResourcesAssembler<UserDtoGet> assembler) {
        Page<UserDtoGet> page = userService.findAll(pageable);
        return assembler.toModel(page);
    }

    @GetMapping("/{nationalId}")
    public ResponseEntity<ResultObject> getUser(@PathVariable long nationalId, HttpServletRequest request) {
        ResultObject result = new ResultObject();
        Client client = new Client();
        MetaData metaData = new MetaData();
        Status status = new Status();
        ResponseEntity<ResultObject> responseEntity;
        UUID userId = userService.getUserId(nationalId);
        UserDtoGet fetchedUser;
        if (request.getRemoteAddr().equals("0:0:0:0:0:0:0:1")) {
            client.setClientIp("localhost");
        } else {
            client.setClientIp(request.getRemoteAddr());
        }
        client.setHttpMethod(request.getMethod());
        metaData.setClient(client);
        if (userId == null) {
            status.setStatusCode(404);
            status.setMessage("User not found");
            metaData.setStatus(status);
            result.setUser(null);
            result.setMetaData(metaData);
            ResultObject savedObject = resultObjectRepository.save(result);
            savedObject.getMetaData().setRequestId(savedObject.getTransactionId());
            responseEntity = new ResponseEntity<>(savedObject, HttpStatus.NOT_FOUND);
        } else {
            fetchedUser = userService.findById(userId);
            status.setStatusCode(200);
            status.setMessage("User found");
            metaData.setStatus(status);
            result.setUser(fetchedUser);
            result.setMetaData(metaData);
            ResultObject savedObject = resultObjectRepository.save(result);
            savedObject.getMetaData().setRequestId(savedObject.getTransactionId());
            responseEntity = ResponseEntity.ok(savedObject);
        }
        return responseEntity;
    }

    @PostMapping
    public ResponseEntity<UserDtoGet> postUser(@RequestBody UserDtoGet userDTOGet) {
        UserDtoGet saved = userService.save(userDTOGet);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("{nationalId}")
    public ResponseEntity<UserDtoPost> putUser(@PathVariable long nationalId, @RequestBody UserDtoPost userDtoPost) {
        UUID userId = userService.getUserId(nationalId);
        if (userId == null) {
            return ResponseEntity.notFound().build();
        } else {
            UserDtoPost updatedByPut = userService.put(userId, userDtoPost);
            return ResponseEntity.ok(updatedByPut);
        }
    }

    @PatchMapping("{nationalId}")
    public ResponseEntity<UserDtoPost> patchUser(@PathVariable long nationalId, @RequestBody UserDtoPost userDtoPost) {
        UUID userId = userService.getUserId(nationalId);
        if (userId == null) {
            return ResponseEntity.notFound().build();
        }
        UserDtoPost updatedByPut = userService.patch(userId, userDtoPost);
        return ResponseEntity.ok(updatedByPut);
    }

    @DeleteMapping("{nationalId}")
    public ResponseEntity<UserDtoGet> deleteUser(@PathVariable long nationalId) {
        UUID userId = userService.getUserId(nationalId);
        if (userId == null) {
            return ResponseEntity.notFound().build();
        } else {
            userService.delete(userId);
            return ResponseEntity.ok().build();
        }
    }
}
