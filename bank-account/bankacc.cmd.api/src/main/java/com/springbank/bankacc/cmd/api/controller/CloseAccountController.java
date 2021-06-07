package com.springbank.bankacc.cmd.api.controller;

import com.springbank.bankacc.cmd.api.commands.CloseAccountCommand;
import com.springbank.bankacc.core.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/closeBankAccount")
@RequiredArgsConstructor
public class CloseAccountController {

    private final CommandGateway commandGateway;

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<BaseResponse> closeBankAccount(@PathVariable("id") String id) {
        try {
            CloseAccountCommand closeAccountCommand = CloseAccountCommand.builder().id(id).build();
            commandGateway.send(closeAccountCommand);
            return new ResponseEntity<>(new BaseResponse("BankAccount closed successfully"), HttpStatus.OK);
        } catch (Exception e) {
            String safeErrorMessage = "request close account request with id = " + id + " failed";
            System.err.println(safeErrorMessage);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
