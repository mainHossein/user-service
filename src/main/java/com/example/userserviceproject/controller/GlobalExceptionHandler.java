package com.example.userserviceproject.controller;

import com.example.userserviceproject.entity.Client;
import com.example.userserviceproject.entity.MetaData;
import com.example.userserviceproject.entity.ResultObject;
import com.example.userserviceproject.entity.Status;
import com.example.userserviceproject.repository.ResultObjectRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ResultObjectRepository resultObjectRepository;
    ResultObject result;
    Client client;
    MetaData metaData;
    Status status;
    ResponseEntity<ResultObject> responseEntity;

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResultObject> typeMisMatched(HttpServletRequest request) {
        result = new ResultObject();
        client = new Client();
        metaData = new MetaData();
        status = new Status();
        status.setStatusCode(400);
        status.setMessage("National id should be only numbers");
        ResultObject savedObject = response(request);
        responseEntity = new ResponseEntity<>(savedObject, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResultObject> wrongPath(HttpServletRequest request) {
        result = new ResultObject();
        client = new Client();
        metaData = new MetaData();
        status = new Status();
        status.setStatusCode(404);
        status.setMessage("Not Found!");
        ResultObject savedObject = response(request);
        responseEntity = new ResponseEntity<>(savedObject, HttpStatus.NOT_FOUND);
        return responseEntity;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResultObject> duplicatedPrimaryKey(HttpServletRequest request) {
        result = new ResultObject();
        client = new Client();
        metaData = new MetaData();
        status = new Status();
        status.setStatusCode(400);
        status.setMessage("User with this national id already exists!");
        ResultObject savedObject = response(request);
        responseEntity = new ResponseEntity<>(savedObject, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResultObject> nullField(HttpServletRequest request) {
        result = new ResultObject();
        client = new Client();
        metaData = new MetaData();
        status = new Status();
        status.setStatusCode(400);
        status.setMessage("Fields shouldn't be null!");
        ResultObject savedObject = response(request);
        responseEntity = new ResponseEntity<>(savedObject, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResultObject> wrongStructure(HttpServletRequest request) {
        result = new ResultObject();
        client = new Client();
        metaData = new MetaData();
        status = new Status();
        status.setStatusCode(400);
        status.setMessage("Wrong structure in data!");
        ResultObject savedObject = response(request);
        responseEntity = new ResponseEntity<>(savedObject, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ResponseEntity<ResultObject> unSupportedMediaType(HttpServletRequest request) {
        result = new ResultObject();
        client = new Client();
        metaData = new MetaData();
        status = new Status();
        status.setStatusCode(415);
        status.setMessage("Unsupported media type!");
        ResultObject savedObject = response(request);
        responseEntity = new ResponseEntity<>(savedObject, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        return responseEntity;
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<ResultObject> unSupportedMethod(HttpServletRequest request) {
        result = new ResultObject();
        client = new Client();
        metaData = new MetaData();
        status = new Status();
        status.setStatusCode(405);
        status.setMessage("Method not allowed!");
        ResultObject savedObject = response(request);
        responseEntity = new ResponseEntity<>(savedObject, HttpStatus.METHOD_NOT_ALLOWED);
        return responseEntity;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResultObject> wrongHeaders(HttpServletRequest request,
                                                     MethodArgumentNotValidException ex) {
        result = new ResultObject();
        client = new Client();
        metaData = new MetaData();
        status = new Status();
        status.setStatusCode(400);
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            status.setMessage(fieldName + " " + message);
        });
        ResultObject savedObject = response(request);
        responseEntity = new ResponseEntity<>(savedObject, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    private ResultObject response(HttpServletRequest request) {
        if (request.getRemoteAddr().equals("0:0:0:0:0:0:0:1")) {
            client.setClientIp("127.0.0.1");
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
        return savedObject;
    }
}
