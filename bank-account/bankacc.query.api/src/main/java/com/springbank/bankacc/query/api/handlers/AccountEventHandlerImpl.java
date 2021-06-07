package com.springbank.bankacc.query.api.handlers;

import com.springbank.bankacc.core.events.AccountClosedEvent;
import com.springbank.bankacc.core.events.AccountOpenEvent;
import com.springbank.bankacc.core.events.FoundsDepositedEvent;
import com.springbank.bankacc.core.events.FoundsWithdrawnEvent;
import com.springbank.bankacc.core.models.BankAccount;
import com.springbank.bankacc.query.api.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@ProcessingGroup("bankaccount-group")
@RequiredArgsConstructor
public class AccountEventHandlerImpl implements AccountEventHandler {

    private final AccountRepository accountRepository;

    @Override
    @EventHandler
    public void on(AccountOpenEvent event) {
        BankAccount bankAccount = BankAccount.builder().id(event.getId())
                .accountHolderId(event.getAccountHolderId())
                .creationDate(LocalDate.now())
                .balance(event.getOpeningBalance())
                .accountType(event.getAccountType())
                .build();
        accountRepository.save(bankAccount);
    }

    @Override
    @EventHandler
    public void on(FoundsDepositedEvent event) {
        accountRepository.findById(event.getId()).ifPresent(bankAccount1 -> {
            bankAccount1.setBalance(event.getBalance());
            accountRepository.save(bankAccount1);
        });
    }

    @Override
    @EventHandler
    public void on(FoundsWithdrawnEvent event) {
        accountRepository.findById(event.getId()).ifPresent(bankAccount1 -> {
            bankAccount1.setBalance(event.getBalance());
            accountRepository.save(bankAccount1);
        });
    }

    @Override
    @EventHandler
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());
    }
}
