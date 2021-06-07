package com.springbank.bankacc.cmd.api.controller;

import com.springbank.bankacc.cmd.api.commands.DepositFoundCommand;
import com.springbank.bankacc.core.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/depositFounds")
@RequiredArgsConstructor
public class DepositFoundsController {

    private final CommandGateway commandGateway;

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<BaseResponse> depositFounds(@PathVariable("id") String id, @Valid @RequestBody DepositFoundCommand depositFoundCommand) {
        try {
            commandGateway.send(depositFoundCommand);
            return new ResponseEntity<>(new BaseResponse("deposit found successfully with id " + depositFoundCommand.getId()), HttpStatus.OK);
        } catch (Exception e) {
            String safeErrorMessage = "deposit found request failure with id=" + depositFoundCommand.getId();
            System.err.println(safeErrorMessage);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
