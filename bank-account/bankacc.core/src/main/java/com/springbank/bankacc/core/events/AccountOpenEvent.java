package com.springbank.bankacc.core.events;

import com.springbank.bankacc.core.models.AccountType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AccountOpenEvent {

    private String id;
    private String accountHolderId;
    private AccountType accountType;
    private LocalDateTime creationDate;
    private double openingBalance;
}
