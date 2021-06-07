package com.springbank.bankacc.cmd.api.controller;

import com.springbank.bankacc.cmd.api.commands.OpenAccountCommand;
import com.springbank.bankacc.cmd.api.dtos.OpenAccountResponse;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/openBankAccount")
@RequiredArgsConstructor
public class OpenAccountController {

    private final CommandGateway commandGateway;

    @PostMapping("")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<OpenAccountResponse> openBankAccount(@Valid @RequestBody OpenAccountCommand command) {
        try {
            command.setId(UUID.randomUUID().toString());
            commandGateway.send(command);
            return new ResponseEntity<>(new OpenAccountResponse(command.getId(), "open bankAccount successfully "), HttpStatus.CREATED);
        } catch (Exception e) {
            String safeErrorMessage = "open bankAccount request failure";
            System.err.println(safeErrorMessage);
            return new ResponseEntity<>(new OpenAccountResponse(command.getId(), safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
