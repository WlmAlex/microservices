package com.springbank.user.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Account {

    @Size(min = 2, message = "username must have minimum of 2 characters")
    private String userName;
    @Size(min = 7, message = "password must have minimum of 7 characters")
    private String password;
    @NotNull(message = "specify at least one role")
    private List<Role> role;
}
