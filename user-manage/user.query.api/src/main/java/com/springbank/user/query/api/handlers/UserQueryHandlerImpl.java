package com.springbank.user.query.api.handlers;

import com.springbank.user.query.api.dto.UserLookupResponse;
import com.springbank.user.query.api.quries.FindAllUsersQuery;
import com.springbank.user.query.api.quries.FindUserByIdQuery;
import com.springbank.user.query.api.quries.SearchUsersQuery;
import com.springbank.user.query.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryHandlerImpl implements UserQueryHandler {

    private final UserRepository userRepository;

    @Override
    @QueryHandler
    public UserLookupResponse getUserById(FindUserByIdQuery findUserByIdQuery) {
        return new UserLookupResponse(userRepository.findById(findUserByIdQuery.getId())
                .orElse(null));
    }

    @Override
    @QueryHandler
    public UserLookupResponse searchUsers(SearchUsersQuery searchUsersQuery) {
        return new UserLookupResponse(userRepository.findByFilterRegex(
                searchUsersQuery.getFilter()));
    }

    @Override
    @QueryHandler
    public UserLookupResponse getAllUsers(FindAllUsersQuery findAllUsersQuery) {
        return new UserLookupResponse(userRepository.findAll());
    }
}
