package com.springbank.bankacc.cmd.api.controller;

import com.springbank.bankacc.cmd.api.commands.WithDrawFoundCommand;
import com.springbank.bankacc.core.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/withDrawFounds")
@RequiredArgsConstructor
public class WithDrawFoundsController {

    private final CommandGateway commandGateway;

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> withDrawFounds(@PathVariable("id") String id,
                                                       @Valid @RequestBody WithDrawFoundCommand command) {
        try {
            commandGateway.send(command).get();
            return new ResponseEntity<>(new BaseResponse("withdraw founds successfully"), HttpStatus.OK);
        } catch (Exception e) {
            String safeErrorMessage = "request withdraw founds request with id = " + id + " failed";
            System.err.println(safeErrorMessage);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
