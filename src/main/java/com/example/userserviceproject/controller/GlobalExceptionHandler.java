package com.example.userserviceproject.controller;

import com.example.userserviceproject.entity.Client;
import com.example.userserviceproject.entity.MetaData;
import com.example.userserviceproject.entity.ResultObject;
import com.example.userserviceproject.entity.Status;
import com.example.userserviceproject.repository.ResultObjectRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ResultObjectRepository resultObjectRepository;
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResultObject> typeMisMatched(Exception e, HttpServletRequest request) {
        ResultObject result = new ResultObject();
        Client client = new Client();
        MetaData metaData = new MetaData();
        Status status = new Status();
        ResponseEntity<ResultObject> responseEntity;
        status.setStatusCode(400);
        status.setMessage("National id should be only numbers");
        if (request.getRemoteAddr().equals("0:0:0:0:0:0:0:1")) {
            client.setClientIp("localhost");
        } else {
            client.setClientIp(request.getRemoteAddr());
        }
        client.setHttpMethod(request.getMethod());
        metaData.setStatus(status);
        metaData.setClient(client);
        result.setUser(null);
        result.setMetaData(metaData);
        ResultObject savedObject = resultObjectRepository.save(result);
        savedObject.getMetaData().setRequestId(savedObject.getTransactionId());
        responseEntity = new ResponseEntity<>(savedObject, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }
}
