package com.example.userserviceproject.controller;

import com.example.userserviceproject.entity.Client;
import com.example.userserviceproject.entity.MetaData;
import com.example.userserviceproject.entity.ResultObject;
import com.example.userserviceproject.entity.Status;
import com.example.userserviceproject.model.UserDtoGet;
import com.example.userserviceproject.model.UserDtoUpdate;
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


    ResultObject result;
    Client client;
    Status status;
    MetaData metaData;
    ResponseEntity<ResultObject> responseEntity;

    @GetMapping
    public PagedModel<EntityModel<UserDtoGet>> getUsers(Pageable pageable,
                                                        PagedResourcesAssembler<UserDtoGet> assembler,
                                                        HttpServletRequest request) {
        result = new ResultObject();
        client = setClientAttribute(request);
        status = new Status();
        metaData = new MetaData();
        status.setStatusCode(200);
        status.setMessage("Users found!");
        metaData.setClient(client);
        metaData.setStatus(status);
        result.setUser(null);
        result.setMetaData(metaData);
        ResultObject savedResult = resultObjectRepository.save(result);
        result.getMetaData().setRequestId(savedResult.getTransactionId());
        Page<UserDtoGet> page = userService.findAll(pageable);
        return assembler.toModel(page);
    }

    @GetMapping("/{nationalId}")
    public ResponseEntity<ResultObject> getUser(@PathVariable long nationalId, HttpServletRequest request) {
        result = new ResultObject();
        client = new Client();
        status = new Status();
        metaData = new MetaData();
        HttpStatus httpStatus;
        UUID userId = userService.getUserId(nationalId);
        UserDtoGet fetchedUser;
        metaData.setClient(client);
        if (userId == null) {
            status.setStatusCode(404);
            status.setMessage("User not found");
            result.setUser(null);
            httpStatus = HttpStatus.NOT_FOUND;
        } else {
            fetchedUser = userService.findById(userId);
            status.setStatusCode(200);
            status.setMessage("User found");
            result.setUser(fetchedUser);
            httpStatus = HttpStatus.OK;
        }
        return getResultObjectResponseEntity(httpStatus, request);
    }

    @PostMapping
    public ResponseEntity<ResultObject> postUser(@RequestBody UserDtoGet userDTOGet,
                                               HttpServletRequest request) {
        result = new ResultObject();
        client = new Client();
        status = new Status();
        metaData = new MetaData();
        HttpStatus httpStatus = HttpStatus.CREATED;
        status.setStatusCode(201);
        status.setMessage("User created!");
        result.setUser(userService.save(userDTOGet));
        return getResultObjectResponseEntity(httpStatus, request);
    }

    @PutMapping("{nationalId}")
    public ResponseEntity<ResultObject> putUser(@PathVariable long nationalId, @RequestBody UserDtoUpdate userDtoUpdate,
                                                 HttpServletRequest request) {
        result = new ResultObject();
        client = new Client();
        status = new Status();
        metaData = new MetaData();
        HttpStatus httpStatus;
        UUID userId = userService.getUserId(nationalId);
        if (userId == null) {
            result.setUser(null);
            status.setStatusCode(404);
            status.setMessage("User not found!");
            httpStatus = HttpStatus.NOT_FOUND;
        } else {
            result.setUser(userService.put(userId, userDtoUpdate));
            status.setStatusCode(202);
            status.setMessage("Accepted!");
            httpStatus = HttpStatus.ACCEPTED;
        }
        return getResultObjectResponseEntity(httpStatus, request);
    }

    @PatchMapping("{nationalId}")
    public ResponseEntity<ResultObject> patchUser(@PathVariable long nationalId, @RequestBody UserDtoUpdate userDtoUpdate,
                                                   HttpServletRequest request) {
        result = new ResultObject();
        client = new Client();
        status = new Status();
        metaData = new MetaData();
        HttpStatus httpStatus;
        UUID userId = userService.getUserId(nationalId);
        if (userId == null) {
            result.setUser(null);
            status.setStatusCode(404);
            status.setMessage("User not found!");
            httpStatus = HttpStatus.NOT_FOUND;
        } else {
            result.setUser(userService.patch(userId, userDtoUpdate));
            status.setStatusCode(202);
            status.setMessage("Accepted!");
            httpStatus = HttpStatus.ACCEPTED;
        }
        return getResultObjectResponseEntity(httpStatus, request);
    }

    @DeleteMapping("{nationalId}")
    public ResponseEntity<ResultObject> deleteUser(@PathVariable long nationalId,
                                                 HttpServletRequest request) {
        result = new ResultObject();
        client = new Client();
        status = new Status();
        metaData = new MetaData();
        HttpStatus httpStatus;
        UUID userId = userService.getUserId(nationalId);
        if (userId == null) {
            status.setStatusCode(404);
            status.setMessage("User not found!");
            httpStatus = HttpStatus.NOT_FOUND;
        } else {
            userService.delete(userId);
            status.setStatusCode(200);
            status.setMessage("Ok!");
            httpStatus = HttpStatus.OK;
        }
        return getResultObjectResponseEntity(httpStatus, request);
    }

    private ResponseEntity<ResultObject> getResultObjectResponseEntity(HttpStatus httpStatus,
                                                                       HttpServletRequest request) {
        if (request.getRemoteAddr().equals("0:0:0:0:0:0:0:1")) {
            client.setClientIp("localhost");
        } else {
            client.setClientIp(request.getRemoteAddr());
        }
        client.setHttpMethod(request.getMethod());
        metaData.setClient(client);
        metaData.setStatus(status);
        result.setMetaData(metaData);
        ResultObject savedResult = resultObjectRepository.save(result);
        result.getMetaData().setRequestId(savedResult.getTransactionId());
        responseEntity = new ResponseEntity<>(result, httpStatus);
        return responseEntity;
    }

    private Client setClientAttribute(HttpServletRequest request) {
        Client client = new Client();
        if (request.getRemoteAddr().equals("0:0:0:0:0:0:0:1")) {
            client.setClientIp("localhost");
        } else {
            client.setClientIp(request.getRemoteAddr());
        }
        client.setHttpMethod(request.getMethod());
        return client;
    }
}
