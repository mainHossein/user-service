package com.example.userserviceproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Embeddable
@Getter
@Setter
@ToString
public class MetaData {
    @Transient
    private UUID requestId;
    @Embedded
    private Status status;
    @Embedded
    @JsonIgnore
    private Client client;
}
