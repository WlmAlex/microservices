package com.springbank.bankacc.cmd.api.commands;

import com.springbank.bankacc.core.models.AccountType;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class OpenAccountCommand {

    @TargetAggregateIdentifier
    private String id;
    @NotNull(message = "No Account holder id was supplied")
    private String accountHolderId;
    @NotNull(message = "No AccountType was supplied")
    private AccountType accountType;
    @Min(value = 50, message = "balance should be greater or equal than 50")
    private double openingBalance;
}
