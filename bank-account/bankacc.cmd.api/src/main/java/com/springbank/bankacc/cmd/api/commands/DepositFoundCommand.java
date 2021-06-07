package com.springbank.bankacc.cmd.api.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.Min;

@Data
@Builder
public class DepositFoundCommand {

    @TargetAggregateIdentifier
    private String id;
    @Min(value = 1, message = "deposit amount must be equal or greater than 1")
    private double amount;
}
