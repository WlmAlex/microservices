package com.springbank.bankacc.query.api.handlers;

import com.springbank.bankacc.core.events.AccountClosedEvent;
import com.springbank.bankacc.core.events.AccountOpenEvent;
import com.springbank.bankacc.core.events.FoundsDepositedEvent;
import com.springbank.bankacc.core.events.FoundsWithdrawnEvent;

public interface AccountEventHandler {

    void on(AccountOpenEvent event);
    void on(FoundsDepositedEvent event);
    void on(FoundsWithdrawnEvent event);
    void on(AccountClosedEvent event);

}
