package com.springbank.user.query.api.controllers;

import com.springbank.user.query.api.dto.UserLookupResponse;
import com.springbank.user.query.api.quries.FindAllUsersQuery;
import com.springbank.user.query.api.quries.FindUserByIdQuery;
import com.springbank.user.query.api.quries.SearchUsersQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/userLookup")
@RequiredArgsConstructor
public class UserLookupController {

    private final QueryGateway queryGateway;

    @GetMapping
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<UserLookupResponse> getAllUsers() {
        try {
            FindAllUsersQuery findAllUsersQuery = new FindAllUsersQuery();
            UserLookupResponse userLookupResponse = queryGateway.query(findAllUsersQuery,
                    ResponseTypes.instanceOf(UserLookupResponse.class)).join();
            if (userLookupResponse == null || CollectionUtils.isEmpty(userLookupResponse.getUsers())) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(userLookupResponse, HttpStatus.OK);
        } catch (Exception e) {
            String safeErrorMessage = "failed to complete getAllUsers request";
            System.out.println(safeErrorMessage);
            return new ResponseEntity<>(new UserLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byId/{id}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<UserLookupResponse> getUserById(@PathVariable("id") String id) {
        try {
            FindUserByIdQuery findUserByIdQuery = new FindUserByIdQuery(id);
            UserLookupResponse userLookupResponse = queryGateway.query(findUserByIdQuery,
                    ResponseTypes.instanceOf(UserLookupResponse.class)).join();
            if (userLookupResponse == null || CollectionUtils.isEmpty(userLookupResponse.getUsers())) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(userLookupResponse, HttpStatus.OK);
        } catch (Exception e) {
            String safeErrorMessage = "failed to complete getUserById request";
            System.out.println(safeErrorMessage);
            return new ResponseEntity<>(new UserLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byFilter/{filter}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<UserLookupResponse> searchUserByFilter(@PathVariable("filter") String filter) {
        try {
            SearchUsersQuery searchUsersQuery = new SearchUsersQuery(filter);
            UserLookupResponse userLookupResponse = queryGateway.query(searchUsersQuery,
                    ResponseTypes.instanceOf(UserLookupResponse.class)).join();
            if (userLookupResponse == null || CollectionUtils.isEmpty(userLookupResponse.getUsers())) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(userLookupResponse, HttpStatus.OK);
        } catch (Exception e) {
            String safeErrorMessage = "failed to complete getUserById request";
            System.out.println(safeErrorMessage);
            return new ResponseEntity<>(new UserLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
