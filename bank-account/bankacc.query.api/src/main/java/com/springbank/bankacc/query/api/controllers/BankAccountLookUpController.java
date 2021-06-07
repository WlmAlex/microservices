package com.springbank.bankacc.query.api.controllers;

import com.springbank.bankacc.core.dto.BaseResponse;
import com.springbank.bankacc.query.api.dtos.AccountLookUpResponse;
import com.springbank.bankacc.query.api.dtos.EqualityType;
import com.springbank.bankacc.query.api.querys.FindAccountByHolderIdQuery;
import com.springbank.bankacc.query.api.querys.FindAccountByIdQuery;
import com.springbank.bankacc.query.api.querys.FindAccountWithBalanceQuery;
import com.springbank.bankacc.query.api.querys.FindAllAccountQuery;
import lombok.RequiredArgsConstructor;
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
@RequestMapping(path = "/api/v1/bankAccountLookUp")
@RequiredArgsConstructor
public class BankAccountLookUpController {

    private final QueryGateway queryGateway;

    @GetMapping
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<BaseResponse> findAllAccounts() {
        try {
            AccountLookUpResponse accountLookUpResponse = queryGateway.query(FindAllAccountQuery.builder().build(), AccountLookUpResponse.class).get();
            if (accountLookUpResponse == null || CollectionUtils.isEmpty(accountLookUpResponse.getAccountList())) {
                return new ResponseEntity<>(new BaseResponse("bank account doesn't exist"), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(accountLookUpResponse, HttpStatus.OK);

        } catch (Exception e) {
            String safeErrorMessage = "Error happened while processing findAllAccounts request";
            System.err.println(safeErrorMessage);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/findAccountByHolderId/{id}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<BaseResponse> findAccountByHolderId(@PathVariable("id") String id) {
        try {
            AccountLookUpResponse accountLookUpResponse = queryGateway.query(new FindAccountByHolderIdQuery(id), AccountLookUpResponse.class).get();
            if (accountLookUpResponse == null || CollectionUtils.isEmpty(accountLookUpResponse.getAccountList())) {
                return new ResponseEntity<>(new BaseResponse("bank account doesn't exist"), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(accountLookUpResponse, HttpStatus.OK);

        } catch (Exception e) {
            String safeErrorMessage = "Error happened while processing findAccountByHolderId request";
            System.err.println(safeErrorMessage);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "findById/{id}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<BaseResponse> findAccountById(@PathVariable("id") String id) {
        try {
            AccountLookUpResponse accountLookUpResponse = queryGateway.query(new FindAccountByIdQuery(id), AccountLookUpResponse.class).get();
            if (accountLookUpResponse == null || CollectionUtils.isEmpty(accountLookUpResponse.getAccountList())) {
                return new ResponseEntity<>(new BaseResponse("bank account doesn't exist"), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(accountLookUpResponse, HttpStatus.OK);

        } catch (Exception e) {
            String safeErrorMessage = "Error happened while processing findAccountById request";
            System.err.println(safeErrorMessage);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/withBalance/{equality}/{balance}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<BaseResponse> findAccountWithBalanceAndEquality(@PathVariable("equality") EqualityType equalityType,
                                                        @PathVariable("balance") double balance) {
        try {
            AccountLookUpResponse accountLookUpResponse = queryGateway.query(new FindAccountWithBalanceQuery(equalityType, balance), AccountLookUpResponse.class).get();
            if (accountLookUpResponse == null || CollectionUtils.isEmpty(accountLookUpResponse.getAccountList())) {
                return new ResponseEntity<>(new BaseResponse("bank account doesn't exist"), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(accountLookUpResponse, HttpStatus.OK);

        } catch (Exception e) {
            String safeErrorMessage = "Error happened while processing findAccountById request";
            System.err.println(safeErrorMessage);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
