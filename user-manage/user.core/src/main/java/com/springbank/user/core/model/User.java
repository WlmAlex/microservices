package com.springbank.user.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;
    @NotEmpty(message = "first name is mandatory")
    private String firstName;
    @NotEmpty(message = "last name is mandatory")
    private String lastName;
    @Email(message = "please provide a valid e-mail address")
    private String emailAddress;
    @NotNull(message = "please provide account credentials")
    @Valid
    private Account account;
}
