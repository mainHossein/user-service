package com.example.userserviceproject.entity;

import com.example.userserviceproject.model.UserDtoGet;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "result")
public class ResultObject {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonIgnore
    @Column(insertable = false, updatable = false)
    private UUID transactionId;
    @Embedded
    @Transient
    private UserDtoGet user;
    @Embedded
    private MetaData metaData;
    @JsonIgnore
    private final String service = "user";
    @CreationTimestamp
    @JsonIgnore
    private Timestamp requestedAt;
}
