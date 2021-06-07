package com.springbank.bankacc.query.api.handlers;

import com.springbank.bankacc.core.models.BankAccount;
import com.springbank.bankacc.query.api.dtos.AccountLookUpResponse;
import com.springbank.bankacc.query.api.dtos.EqualityType;
import com.springbank.bankacc.query.api.querys.FindAccountByHolderIdQuery;
import com.springbank.bankacc.query.api.querys.FindAccountByIdQuery;
import com.springbank.bankacc.query.api.querys.FindAccountWithBalanceQuery;
import com.springbank.bankacc.query.api.querys.FindAllAccountQuery;
import com.springbank.bankacc.query.api.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountQueryHandlerImpl implements AccountQueryHandler {

    private final AccountRepository accountRepository;

    @Override
    @QueryHandler
    public AccountLookUpResponse findAccountById(FindAccountByIdQuery query) {
        Optional<BankAccount> optionalBankAccount = accountRepository.findById(query.getId());
        return optionalBankAccount.isPresent() ? new AccountLookUpResponse(optionalBankAccount.get(), "found Bank Account with id=" + query.getId())
                : new AccountLookUpResponse("No Bank Account found for id " + query.getId());
    }

    @Override
    @QueryHandler
    public AccountLookUpResponse findAccountByHolderId(FindAccountByHolderIdQuery query) {
        Optional<BankAccount> optionalBankAccount = accountRepository.findByAccountHolderId(query.getAccountHolderId());
        return optionalBankAccount.isPresent() ?
                new AccountLookUpResponse(optionalBankAccount.get(), "found Bank Account with holderId = " + query.getAccountHolderId())
                : new AccountLookUpResponse("No Bank Account found for holderId " + query.getAccountHolderId());
    }

    @Override
    @QueryHandler
    public AccountLookUpResponse findAllAccounts(FindAllAccountQuery query) {
        List<BankAccount> bankAccountList = new ArrayList<>();
        Iterable<BankAccount> bankAccountIterable = accountRepository.findAll();
        if (!bankAccountIterable.iterator().hasNext()) {
            return new AccountLookUpResponse("No Bank Account found");
        }
        bankAccountIterable.forEach(bankAccount -> bankAccountList.add(bankAccount));
        return new AccountLookUpResponse(bankAccountList, "found Bank Accounts Successfully");
    }

    @Override
    @QueryHandler
    public AccountLookUpResponse findAccountsWithBalance(FindAccountWithBalanceQuery query) {
        List<BankAccount> bankAccountList = query.getEqualityType() == EqualityType.GREATER_THAN
                ? accountRepository.findByBalanceGreaterThan(query.getBalance())
                : accountRepository.findByBalanceLessThan(query.getBalance());
        if (CollectionUtils.isEmpty(bankAccountList)) return new AccountLookUpResponse("No Bank Account found");
        return new AccountLookUpResponse(bankAccountList, "found Bank Accounts Successfully");
    }
}
