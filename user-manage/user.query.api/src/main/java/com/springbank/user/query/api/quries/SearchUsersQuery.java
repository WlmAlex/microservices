package com.springbank.user.query.api.quries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchUsersQuery {

    private String filter;
}
