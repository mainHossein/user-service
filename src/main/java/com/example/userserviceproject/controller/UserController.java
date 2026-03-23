package com.example.userserviceproject.controller;

import com.example.userserviceproject.entity.Client;
import com.example.userserviceproject.entity.MetaData;
import com.example.userserviceproject.entity.ResultObject;
import com.example.userserviceproject.entity.Status;
import com.example.userserviceproject.model.UserDtoGetAndPost;
import com.example.userserviceproject.model.UserDtoUpdate;
import com.example.userserviceproject.repository.ResultObjectRepository;
import com.example.userserviceproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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


    private ResultObject result;
    private Client client;
    private Status status;
    private MetaData metaData;

    @GetMapping("/check-national-id/{nationalId}")
    public ResponseEntity<ResultObject> checkNationalId(@PathVariable long nationalId, HttpServletRequest request) {
        result = new ResultObject();
        client = new Client();
        status = new Status();
        metaData = new MetaData();
        HttpStatus httpStatus;
        result.setUser(null);
        if (userService.checkUserExists(nationalId)) {
            status.setStatusCode(200);
            status.setMessage("User exists");
            httpStatus = HttpStatus.OK;
        } else {
            status.setStatusCode(404);
            status.setMessage("User not exists");
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return getResultObjectResponseEntity(httpStatus, request);
    }

    @GetMapping("/{nationalId}")
    public ResponseEntity<ResultObject> getUser(@PathVariable long nationalId, HttpServletRequest request) {
        result = new ResultObject();
        client = new Client();
        status = new Status();
        metaData = new MetaData();
        HttpStatus httpStatus;
        UUID userId = userService.getUserId(nationalId);
        if (userId == null) {
            status.setStatusCode(404);
            status.setMessage("User not found");
            result.setUser(null);
            httpStatus = HttpStatus.NOT_FOUND;
        } else {
            UserDtoGetAndPost fetchedUser = userService.findById(userId);
            status.setStatusCode(200);
            status.setMessage("User found");
            result.setUser(fetchedUser);
            httpStatus = HttpStatus.OK;
        }
        return getResultObjectResponseEntity(httpStatus, request);
    }

    @PostMapping
    public ResponseEntity<ResultObject> postUser(@RequestBody UserDtoGetAndPost userDTOGetAndPost,
                                               HttpServletRequest request) {
        result = new ResultObject();
        client = new Client();
        status = new Status();
        metaData = new MetaData();
        HttpStatus httpStatus = HttpStatus.CREATED;
        status.setStatusCode(201);
        status.setMessage("User created!");
        result.setUser(userService.save(userDTOGetAndPost));
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
            client.setClientIp("127.0.0.1");
        } else {
            client.setClientIp(request.getRemoteAddr());
        }
        client.setHttpMethod(request.getMethod());
        metaData.setClient(client);
        metaData.setStatus(status);
        result.setMetaData(metaData);
        ResultObject savedResult = resultObjectRepository.save(result);
        result.getMetaData().setRequestId(savedResult.getTransactionId());
        return new ResponseEntity<>(result, httpStatus);
    }

}
