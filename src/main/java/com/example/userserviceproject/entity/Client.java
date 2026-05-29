package com.example.userserviceproject.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Embeddable
public class Client {
    private String clientIp;
    private String httpMethod;
}
