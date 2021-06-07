package com.springbank.user.cmd.api.aggregates;

import com.springbank.user.cmd.api.commands.RegisterUserCommand;
import com.springbank.user.cmd.api.commands.RemoveUserCommand;
import com.springbank.user.cmd.api.commands.UpdateUserCommand;
import com.springbank.user.cmd.api.security.PasswordEncoderImpl;
import com.springbank.user.core.events.UserRegisteredEvent;
import com.springbank.user.core.events.UserRemovedEvent;
import com.springbank.user.core.events.UserUpdatedEvent;
import com.springbank.user.core.model.User;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
public class UserAggregate {

    private final PasswordEncoderImpl passwordEncoderImpl;
    @AggregateIdentifier
    private String id;
    private User user;

    public UserAggregate() {
        this.passwordEncoderImpl = new PasswordEncoderImpl();
    }

    @CommandHandler
    public UserAggregate(RegisterUserCommand registerUserCommand) {
        passwordEncoderImpl = new PasswordEncoderImpl();
        User user = registerUserCommand.getUser();
        user.setId(registerUserCommand.getId());
        String password = user.getAccount().getPassword();
        String hashedPassword = passwordEncoderImpl.hashPassword(password);
        user.getAccount().setPassword(hashedPassword);

        UserRegisteredEvent userRegisteredEvent = UserRegisteredEvent.builder().id(registerUserCommand.getId())
                .user(user)
                .build();
        AggregateLifecycle.apply(userRegisteredEvent);
    }

    @CommandHandler
    public void handle(UpdateUserCommand updateUserCommand) {
        User updateUser = updateUserCommand.getUser();
        updateUser.setId(updateUserCommand.getId());
        String password = updateUser.getAccount().getPassword();
        String hashedPassword = passwordEncoderImpl.hashPassword(password);
        updateUser.getAccount().setPassword(hashedPassword);

        UserUpdatedEvent userUpdatedEvent = UserUpdatedEvent.builder().id(UUID.randomUUID().toString())
                .user(updateUser)
                .build();

        AggregateLifecycle.apply(userUpdatedEvent);
    }

    @CommandHandler
    public void handle(RemoveUserCommand removeUserCommand) {
        String userCommandId = removeUserCommand.getId();

        UserRemovedEvent userRemovedEvent = UserRemovedEvent.builder().id(userCommandId).build();
        AggregateLifecycle.apply(userRemovedEvent);

    }

    @EventSourcingHandler
    public void on(UserRegisteredEvent userRegisteredEvent) {
        this.id = userRegisteredEvent.getId();
        this.user = userRegisteredEvent.getUser();
    }


    @EventSourcingHandler
    public void on(UserUpdatedEvent userUpdatedEvent) {
        this.user = userUpdatedEvent.getUser();
    }

    @EventSourcingHandler
    public void on(UserRemovedEvent userRemovedEvent) {
        AggregateLifecycle.markDeleted();
    }
}
