package com.springbank.bankacc.query.api.dtos;

import com.springbank.bankacc.core.dto.BaseResponse;
import com.springbank.bankacc.core.models.BankAccount;

import java.util.ArrayList;
import java.util.List;

public class AccountLookUpResponse extends BaseResponse {

    private List<BankAccount> accountList;
    public AccountLookUpResponse(String message) {
        super(message);
    }

    public AccountLookUpResponse(List<BankAccount> bankAccountList, String message) {
        super(message);
        this.accountList = bankAccountList;
    }

    public AccountLookUpResponse(BankAccount bankAccount, String message) {
        super(message);
        this.accountList = new ArrayList<>();
        accountList.add(bankAccount);
    }

    public List<BankAccount> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<BankAccount> accountList) {
        this.accountList = accountList;
    }
}
