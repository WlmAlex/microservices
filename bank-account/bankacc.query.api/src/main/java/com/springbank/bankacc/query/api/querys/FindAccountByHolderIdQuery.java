package com.springbank.bankacc.query.api.querys;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountByHolderIdQuery {

    private String accountHolderId;
}
