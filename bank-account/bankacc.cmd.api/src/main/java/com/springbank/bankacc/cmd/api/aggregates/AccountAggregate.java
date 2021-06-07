package com.springbank.bankacc.cmd.api.aggregates;

import com.springbank.bankacc.cmd.api.commands.CloseAccountCommand;
import com.springbank.bankacc.cmd.api.commands.DepositFoundCommand;
import com.springbank.bankacc.cmd.api.commands.OpenAccountCommand;
import com.springbank.bankacc.cmd.api.commands.WithDrawFoundCommand;
import com.springbank.bankacc.core.events.AccountClosedEvent;
import com.springbank.bankacc.core.events.AccountOpenEvent;
import com.springbank.bankacc.core.events.FoundsDepositedEvent;
import com.springbank.bankacc.core.events.FoundsWithdrawnEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.LocalDateTime;

@NoArgsConstructor
@Aggregate
public class AccountAggregate {

    @AggregateIdentifier
    private String id;
    private String accountHolderId;
    private double balance;

    @CommandHandler
    public AccountAggregate(OpenAccountCommand command) {
        AccountOpenEvent accountOpenEvent = AccountOpenEvent.builder()
                .id(command.getId())
                .accountHolderId(command.getAccountHolderId())
                .accountType(command.getAccountType())
                .openingBalance(command.getOpeningBalance())
                .creationDate(LocalDateTime.now())
                .build();
        AggregateLifecycle.apply(accountOpenEvent);
    }

    @EventSourcingHandler
    public void on(AccountOpenEvent event) {
        this.id = event.getId();
        this.accountHolderId = event.getAccountHolderId();
        this.balance = event.getOpeningBalance();
    }

    @CommandHandler
    public void handle(DepositFoundCommand command) {
        FoundsDepositedEvent foundsDepositedEvent = FoundsDepositedEvent.builder()
                .id(command.getId())
                .amount(command.getAmount())
                .balance(this.balance + command.getAmount())
                .build();
        AggregateLifecycle.apply(foundsDepositedEvent);
    }

    @EventSourcingHandler
    public void on(FoundsDepositedEvent event) {
        this.balance += event.getAmount();
    }

    @CommandHandler
    public void handle(WithDrawFoundCommand command) {
        if (this.balance - command.getAmount()< 0) {
            throw new IllegalStateException(" Withdraw declined, Insufficient founds");
        }
        FoundsWithdrawnEvent foundsWithdrawnEvent = FoundsWithdrawnEvent.builder()
                .id(command.getId())
                .amount(command.getAmount())
                .balance(balance - command.getAmount())
                .build();
        AggregateLifecycle.apply(foundsWithdrawnEvent);
    }

    @EventSourcingHandler
    public void on(FoundsWithdrawnEvent event) {
        this.balance -=event.getAmount();
    }

    @CommandHandler
    public void handle(CloseAccountCommand command) {
        AccountClosedEvent accountClosedEvent = AccountClosedEvent.builder().id(command.getId()).build();
        AggregateLifecycle.apply(accountClosedEvent);
    }

    @EventSourcingHandler
    public void on(AccountClosedEvent event) {
        AggregateLifecycle.markDeleted();
    }
}
