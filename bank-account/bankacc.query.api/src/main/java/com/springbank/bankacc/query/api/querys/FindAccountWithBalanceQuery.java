package com.springbank.bankacc.query.api.querys;

import com.springbank.bankacc.query.api.dtos.EqualityType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountWithBalanceQuery {

    private EqualityType equalityType;
    private double balance;
}
