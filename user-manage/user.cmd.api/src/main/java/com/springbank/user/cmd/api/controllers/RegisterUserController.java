package com.springbank.user.cmd.api.controllers;

import com.springbank.user.cmd.api.commands.RegisterUserCommand;
import com.springbank.user.cmd.api.dto.RegisterUserResponse;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/registerUser")
public class RegisterUserController {

    private final CommandGateway commandGateway;

    @PostMapping
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody @Valid RegisterUserCommand registerUserCommand) {

        registerUserCommand.setId(UUID.randomUUID().toString());
        try {
            commandGateway.send(registerUserCommand);
            return new ResponseEntity<>(new RegisterUserResponse("User Successfully Registered"), HttpStatus.CREATED);
        } catch (Exception e) {
            String saveErrorMessage = "Error while processing register user request with id = "
                    + registerUserCommand.getId();
            System.out.println(e.toString());
            return new ResponseEntity<>(new RegisterUserResponse(saveErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}