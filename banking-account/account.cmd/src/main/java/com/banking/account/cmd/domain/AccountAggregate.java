package com.banking.account.cmd.domain;

import com.banking.account.cmd.api.command.OpenAccountCommand;
import com.banking.account.common.events.AccountClosedEvent;
import com.banking.account.common.events.AccountOpenedEvent;
import com.banking.account.common.events.FundsDepositedEvent;
import com.banking.account.common.events.FundsWithdrawnEvent;
import com.banking.cqrs.core.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {

    private boolean active;
    private BigDecimal balance;

    public BigDecimal getBalance() {
        return this.balance;
    }

    public AccountAggregate(OpenAccountCommand command) {
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .createdDate(LocalDateTime.now())
                .accountType(command.getAccountType())
                .openingBalance(command.getOpeningBalance())
                .build());
    }

    public void apply(AccountOpenedEvent event) {
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFunds(BigDecimal amount) {
        if (!this.active) {
            throw new IllegalStateException("Funds cannot be deposited in this account.");
        }

        if (amount.compareTo(new BigDecimal("0")) <= 0) {
            throw new IllegalStateException("Cash deposit cannot be zero or less than zero.");
        }

        raiseEvent(FundsDepositedEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());

    }

    public void apply(FundsDepositedEvent event) {
        this.id = event.getId();
        this.balance.add(event.getAmount());
    }

    public void withdrawFunds(BigDecimal amount) {
        if(!this.active){
            throw new IllegalStateException("La cuenta bancaria esta cerrada");
        }
        raiseEvent(FundsWithdrawnEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundsWithdrawnEvent event) {
        this.id = event.getId();
        this.balance.subtract(event.getAmount());
    }

    public void closeAccount() {
        if(!this.active) {
            throw new IllegalStateException("La cuenta de banco esta cerrada");
        }

        raiseEvent(AccountClosedEvent.builder()
                .id(this.id)
                .build());
    }

    public void apply(AccountClosedEvent event) {
        this.id = event.getId();
        this.active = false;
    }
}
