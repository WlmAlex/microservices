package com.springbank.user.cmd.api.controllers;

import com.springbank.user.cmd.api.commands.UpdateUserCommand;
import com.springbank.user.core.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/updateUser")
@RequiredArgsConstructor
public class UpdateUserController {

    private final CommandGateway commandGateway;

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<BaseResponse> updateUser(@PathVariable("id") String id,
                                                   @Valid @RequestBody UpdateUserCommand updateUserCommand) {
        try {
            updateUserCommand.setId(id);
            commandGateway.send(updateUserCommand);
            return new ResponseEntity<>(new BaseResponse("User Successfully Updated"), HttpStatus.OK);
        } catch (Exception e) {
            String updateErrorMessage = "Error while processing update user request with id = "
                    + updateUserCommand.getId();
            System.out.println(e.toString());
            return new ResponseEntity<>(new BaseResponse(updateErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
