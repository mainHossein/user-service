package com.example.userserviceproject.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.sql.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserDtoGet {
    private Long nationalId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Date birthDate;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        UserDtoGet userDTOGet = (UserDtoGet) o;
        return Objects.equals(nationalId, userDTOGet.nationalId) && Objects.equals(firstName, userDTOGet.firstName) && Objects.equals(lastName, userDTOGet.lastName) && Objects.equals(email, userDTOGet.email) && Objects.equals(phoneNumber, userDTOGet.phoneNumber) && Objects.equals(birthDate, userDTOGet.birthDate);
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(nationalId);
        result = 31 * result + Objects.hashCode(firstName);
        result = 31 * result + Objects.hashCode(lastName);
        result = 31 * result + Objects.hashCode(email);
        result = 31 * result + Objects.hashCode(phoneNumber);
        result = 31 * result + Objects.hashCode(birthDate);
        return result;
    }
}
