package com.springbank.user.query.api.handlers;

import com.springbank.user.query.api.dto.UserLookupResponse;
import com.springbank.user.query.api.quries.FindAllUsersQuery;
import com.springbank.user.query.api.quries.FindUserByIdQuery;
import com.springbank.user.query.api.quries.SearchUsersQuery;

public interface UserQueryHandler {

    UserLookupResponse getUserById(FindUserByIdQuery findUserByIdQuery);

    UserLookupResponse searchUsers(SearchUsersQuery searchUsersQuery);

    UserLookupResponse getAllUsers(FindAllUsersQuery findAllUsersQuery);
}
