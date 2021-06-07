package com.springbank.bankacc.query.api.handlers;

import com.springbank.bankacc.query.api.dtos.AccountLookUpResponse;
import com.springbank.bankacc.query.api.querys.FindAccountByHolderIdQuery;
import com.springbank.bankacc.query.api.querys.FindAccountByIdQuery;
import com.springbank.bankacc.query.api.querys.FindAccountWithBalanceQuery;
import com.springbank.bankacc.query.api.querys.FindAllAccountQuery;

import java.rmi.NoSuchObjectException;

public interface AccountQueryHandler {

    AccountLookUpResponse findAccountById(FindAccountByIdQuery query);
    AccountLookUpResponse findAccountByHolderId(FindAccountByHolderIdQuery query);
    AccountLookUpResponse findAllAccounts(FindAllAccountQuery query);
    AccountLookUpResponse findAccountsWithBalance(FindAccountWithBalanceQuery query);

}
